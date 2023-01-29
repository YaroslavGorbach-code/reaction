package yaroslavgorbach.reaction.feature.exercise.extraNumber.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberActions
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberUiMessage
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberViewState
import yaroslavgorbach.reaction.feature.exercise.extraNumber.presentation.ExtraNumberViewModel
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationUiMessage
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
        onMessageShown = viewModel::clearMessage,
        actioner = { action ->
            when (action) {
                is ExtraNumberActions.Back -> onBackClick()
                is ExtraNumberActions.Repeat -> onRepeatExerciseClick()
                else -> viewModel.submitAction(action)
            }
        })
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ExtraNumbers(
    state: ExtraNumberViewState,
    actioner: (ExtraNumberActions) -> Unit,
    onMessageShown: (id: Long) -> Unit,
) {

    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(ExtraNumberActions.Back) },
            onRepeatExercise = { actioner(ExtraNumberActions.Repeat) }
        )
    } else {
        Column {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(ExtraNumberActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(exerciseName = ExerciseName.EXTRA_NUMBER)
                        ),
                        timer = state.timerState.timeUtilFinishedString,
                        content = {
                            state.message?.let { message ->
                                when (message.message) {
                                    ExtraNumberUiMessage.AnswerIsCorrect -> {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_dot_green),
                                            contentDescription = "",
                                            tint = Color.Green,
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(top = 14.dp, end = 27.dp)
                                        )
                                    }

                                    ExtraNumberUiMessage.AnswerIsNotCorrect -> {
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
                    columns = GridCells.Adaptive(60.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    state.numberPacks.firstOrNull()?.numbers?.let { numbers ->
                        items(numbers) { number ->
                            NumberItem(
                                number = number,
                                onNumberClick = { actioner(ExtraNumberActions.NumberClick(number)) })
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
        ExtraNumbers(state = ExtraNumberViewState(numberPacks = listOf(NumberPack.Test)), actioner = {}, onMessageShown = {})
    }
}