package yaroslavgorbach.reaction.feature.exercise.extranumber.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberActions
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberViewState
import yaroslavgorbach.reaction.feature.exercise.extranumber.presentation.ExtraNumberViewModel
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesViewState
import yaroslavgorbach.reaction.utill.TimerCountDown

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExtraNumbers(
    onBackClick: () -> Unit,
) {
    ExtraNumbers(
        viewModel = viewModel(),
        onBackClick = onBackClick,
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraNumbers(
    viewModel: ExtraNumberViewModel,
    onBackClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()
    val context = LocalContext.current

    ExtraNumbers(
        state = viewState.value,
    ) { action ->
        Toast.makeText(context, viewState.value.pointsCorrect.toString(), Toast.LENGTH_SHORT).show()

        when (action) {
            is ExtraNumberActions.OnBackAction -> onBackClick()
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

    Box(Modifier.fillMaxSize()) {
        when (state.timerState) {
            TimerCountDown.TimerState.Finish -> actioner(ExtraNumberActions.OnExerciseFinish)
            is TimerCountDown.TimerState.Tick -> {
                ExerciseTopBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    instruction = stringResource(id = ExerciseNameToInstructionResMapper.map(exerciseName = ExerciseName.TEST)),
                    timeProgress = state.timerState.timeUtilFinishedProgress,
                    time = state.timerState.timeUtilFinishedString
                )
            }
        }

        LazyVerticalGrid(cells = GridCells.Adaptive(100.dp), modifier = Modifier.align(Alignment.Center)) {
            items(state.numberPacks.firstOrNull()?.numbers ?: emptyList()) { number ->
                NumberItem(number = number, onNumberClick = { actioner(ExtraNumberActions.NumberClick(number)) })
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