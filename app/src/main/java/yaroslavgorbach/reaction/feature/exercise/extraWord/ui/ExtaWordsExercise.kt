package yaroslavgorbach.reaction.feature.exercise.extraWord.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordActions
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordUiMessage
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordViewState
import yaroslavgorbach.reaction.feature.exercise.extraWord.presentation.ExtraWordViewModel
import yaroslavgorbach.reaction.utill.TimerCountDown
import yaroslavgorbach.reaction.utill.findActivity

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
        onMessageShown = viewModel::clearMessage,
        actioner = { action ->
            when (action) {
                is ExtraWordActions.Back -> onBackClick()
                is ExtraWordActions.Repeat -> onRepeatExerciseClick()
                else -> viewModel.submitAction(action)
            }
        }
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraWords(
    state: ExtraWordViewState,
    actioner: (ExtraWordActions) -> Unit,
    onMessageShown: (id: Long) -> Unit,
) {
    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(ExtraWordActions.Back) },
            onRepeatExercise = { actioner(ExtraWordActions.Repeat) }
        )
    } else {
        Column {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(ExtraWordActions.FinishExercise(requireNotNull(LocalContext.current.findActivity())))
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(
                                exerciseName = ExerciseName.EXTRA_WORD
                            )
                        ),
                        timer = state.timerState.timeUtilFinishedString,
                        content = {
                            state.message?.let { message ->
                                when (message.message) {
                                    ExtraWordUiMessage.AnswerIsCorrect -> {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_dot_green),
                                            contentDescription = "",
                                            tint = Color.Green,
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(top = 14.dp, end = 27.dp)
                                        )
                                    }

                                    ExtraWordUiMessage.AnswerIsNotCorrect -> {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_dot_red),
                                            contentDescription = "",
                                            tint = Color.Red,
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(top = 14.dp, end = 27.dp)
                                        )
                                    }
                                }
                                onMessageShown(message.id)
                            }
                        }
                    )
                }
            }

            Box(Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(8.dp),
                    columns = GridCells.Adaptive(70.dp),
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
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        ExtraWords(state = ExtraWordViewState(wordPacks = listOf(WordPack.Test)), actioner = {}, onMessageShown = {})
    }
}