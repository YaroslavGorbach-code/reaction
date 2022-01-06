package yaroslavgorbach.reaction.feature.exercise.extraWord.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordActions
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordViewState
import yaroslavgorbach.reaction.feature.exercise.extraWord.presentation.ExtraWordViewModel
import yaroslavgorbach.reaction.utill.TimerCountDown

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExtraWords(
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    ExtraWords(
        viewModel = hiltViewModel(),
        onBackClick = onBackClick,
        onRepeatExerciseClick = onRepeatExerciseClick
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraWords(
    viewModel: ExtraWordViewModel,
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    ExtraWords(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExtraWordActions.Back -> onBackClick()
            is ExtraWordActions.Repeat -> onRepeatExerciseClick()
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraWords(
    state: ExtraWordViewState,
    actioner: (ExtraWordActions) -> Unit,
) {
    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(ExtraWordActions.Back) },
            onRepeatExercise = { actioner(ExtraWordActions.Repeat) }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(ExtraWordActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        modifier = Modifier.align(Alignment.TopCenter),
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(
                                exerciseName = ExerciseName.EXTRA_WORD
                            )
                        ),
                        timeProgress = state.timerState.timeUntilFinishedProgress,
                        onBack = { actioner(ExtraWordActions.Back) }
                    )
                }
            }
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(100.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    state.wordPacks.firstOrNull()?.words?.let { words ->
                        items(words) { word ->
                            WordItem(
                                word = word,
                                onWordClick = { actioner(ExtraWordActions.WordClick(word)) })
                        }
                    }
                }
            }
        }
    }


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        ExtraWords(state = ExtraWordViewState(wordPacks = listOf(WordPack.Empty)), actioner = {})
    }
}