package yaroslavgorbach.reaction.feature.timer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography
import yaroslavgorbach.reaction.feature.timer.model.ExerciseTimerActions
import yaroslavgorbach.reaction.feature.timer.model.ExerciseTimerViewState
import yaroslavgorbach.reaction.feature.timer.presentation.TimerViewModel
import yaroslavgorbach.reaction.utill.TimerCountDown

@ExperimentalMaterialApi
@Composable
fun ExerciseTimer(
    openExercise: (exerciseName: ExerciseName) -> Unit,
    onBackClick: () -> Unit,
) {
    val viewModel: TimerViewModel = hiltViewModel()

    ExerciseTimer(
        viewModel = viewModel,
        openExercise = openExercise,
        onBack = onBackClick
    )
}

@ExperimentalMaterialApi
@Composable
internal fun ExerciseTimer(
    viewModel: TimerViewModel,
    openExercise: (exerciseName: ExerciseName) -> Unit,
    onBack: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    ExerciseTimer(state = viewState.value) { action ->
        when (action) {
            is ExerciseTimerActions.OpenExercise -> openExercise(action.exerciseName)
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalMaterialApi
@Composable
internal fun ExerciseTimer(
    state: ExerciseTimerViewState,
    actioner: (ExerciseTimerActions) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.timerState) {
            TimerCountDown.TimerState.Finish -> {
                LaunchedEffect(Unit){
                    actioner(ExerciseTimerActions.OpenExercise(state.exerciseName))
                }
            }
            is TimerCountDown.TimerState.Tick -> {
                Text(
                    text = state.timerState.secondsUntilFinished.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    style = AppTypography.h1.copy(fontSize = 88.sp)
                )
            }
        }
    }
}