package yaroslavgorbach.reaction.feature.exercise.extranumber.ui

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
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.model.ExerciseResultUi
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberActions
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberViewState
import yaroslavgorbach.reaction.feature.exercise.extranumber.presentation.ExtraNumberViewModel
import yaroslavgorbach.reaction.utill.TimerCountDown

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExtraNumbers(
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    ExtraNumbers(
        viewModel = hiltViewModel(),
        onBackClick = onBackClick,
        onRepeatExerciseClick = onRepeatExerciseClick
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraNumbers(
    viewModel: ExtraNumberViewModel,
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    ExtraNumbers(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExtraNumberActions.Back -> onBackClick()
            is ExtraNumberActions.Repeat -> onRepeatExerciseClick()
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraNumbers(
    state: ExtraNumberViewState,
    actioner: (ExtraNumberActions) -> Unit,
) {

    if (state.isFinished) {
        ExerciseResult(
            exerciseResultUi = ExerciseResultUi(
                exerciseName = ExerciseName.TEST,
                correctPoints = state.pointsCorrect,
                incorrectPoints = state.pointsIncorrect,
            ),
            onBackClick = { actioner(ExtraNumberActions.Back) },
            onRepeatExercise = { actioner(ExtraNumberActions.Repeat) }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(ExtraNumberActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        modifier = Modifier.align(Alignment.TopCenter),
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(
                                exerciseName = ExerciseName.TEST
                            )
                        ),
                        timeProgress = state.timerState.timeUtilFinishedProgress,
                        time = state.timerState.timeUtilFinishedString,
                        onBack = { actioner(ExtraNumberActions.Back) }
                    )

                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(100.dp),
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        state.numberPacks.firstOrNull()?.numbers?.let { numbers ->
                            items(numbers) { number ->
                                NumberItem(
                                    number = number,
                                    onNumberClick = { actioner(ExtraNumberActions.NumberClick(number)) })
                            }
                        } ?: run { actioner(ExtraNumberActions.FinishExercise) }
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
        ExtraNumbers(state = ExtraNumberViewState(numberPacks = listOf(NumberPack.Test)), actioner = {})
    }
}