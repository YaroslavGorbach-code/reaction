package yaroslavgorbach.reaction.feature.exercise.rotation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.common.ui.YesNoBottomButtons
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationActions
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationViewState
import yaroslavgorbach.reaction.feature.exercise.rotation.presentation.RotationViewModel
import yaroslavgorbach.reaction.utill.TimerCountDown


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@InternalCoroutinesApi
@Composable
fun RotationExercise(
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    RotationExercise(
        viewModel = hiltViewModel(),
        onBackClick = onBackClick,
        onRepeatExerciseClick = onRepeatExerciseClick
    )
}

@InternalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun RotationExercise(
    viewModel: RotationViewModel,
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    RotationExercise(
        state = viewState.value,
    ) { action ->
        when (action) {
            is RotationActions.Back -> onBackClick()
            is RotationActions.Repeat -> onRepeatExerciseClick()
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun RotationExercise(
    state: RotationViewState,
    actioner: (RotationActions) -> Unit,
) {
    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(RotationActions.Back) },
            onRepeatExercise = { actioner(RotationActions.Repeat) }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(RotationActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        modifier = Modifier.align(Alignment.TopCenter),
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(
                                exerciseName = ExerciseName.ROTATION
                            )
                        ),
                        timeProgress = state.timerState.timeUntilFinishedProgress,
                        time = state.timerState.timeUtilFinishedString,
                        onBack = { actioner(RotationActions.Back) }
                    )
                }
            }

            Column(modifier = Modifier.align(Alignment.Center)) {
                Table(table = state.tables.firstTable)
                Table(table = state.tables.secondTable)
            }

            YesNoBottomButtons(onClick = { actioner(RotationActions.OnChose(it)) })
        }
    }
}



@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        RotationExercise(state = RotationViewState.Test, actioner = {})
    }
}