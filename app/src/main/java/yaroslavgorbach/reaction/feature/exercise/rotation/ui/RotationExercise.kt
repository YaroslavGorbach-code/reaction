package yaroslavgorbach.reaction.feature.exercise.rotation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.InternalCoroutinesApi
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.common.ui.YesNoBottomButtons
import yaroslavgorbach.reaction.feature.exercise.cpmplexSort.model.ComplexSortUiMessage
import yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model.NumbersAndLettersUiMessage
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationActions
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationUiMessage
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
        onMessageShown = viewModel::clearMessage,
        actioner = { action ->
            when (action) {
                is RotationActions.Back -> onBackClick()
                is RotationActions.Repeat -> onRepeatExerciseClick()
                else -> viewModel.submitAction(action)
            }
        }
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun RotationExercise(
    state: RotationViewState,
    actioner: (RotationActions) -> Unit,
    onMessageShown: (id: Long) -> Unit,
) {
    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(RotationActions.Back) },
            onRepeatExercise = { actioner(RotationActions.Repeat) }
        )
    } else {
        Column {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(RotationActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(exerciseName = ExerciseName.ROTATION)
                        ),
                        timer = state.timerState.timeUtilFinishedString,
                        content = {
                            state.message?.let { message ->
                                when (message.message) {
                                    RotationUiMessage.AnswerIsCorrect -> {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_dot_green),
                                            contentDescription = "",
                                            tint = Color.Green,
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(top = 14.dp, end = 27.dp)
                                        )
                                    }

                                    RotationUiMessage.AnswerIsNotCorrect -> {
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
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Table(table = state.tables.firstTable)
                    Table(table = state.tables.secondTable)
                }

                YesNoBottomButtons(
                    modifier = Modifier.padding(16.dp).align(Alignment.BottomCenter),
                    onClick = { actioner(RotationActions.OnChose(it)) }
                )
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
        RotationExercise(state = RotationViewState.Test, actioner = {}, onMessageShown = {})
    }
}