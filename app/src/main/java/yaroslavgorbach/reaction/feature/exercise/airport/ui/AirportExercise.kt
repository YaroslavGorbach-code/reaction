package yaroslavgorbach.reaction.feature.exercise.airport.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.East
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.West
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.airport.model.Direction
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportActions
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportViewState
import yaroslavgorbach.reaction.feature.exercise.airport.presentation.AirportViewModel
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.utill.TimerCountDown

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AirportExercise(
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    AirportExercise(
        viewModel = hiltViewModel(),
        onBackClick = onBackClick,
        onRepeatExerciseClick = onRepeatExerciseClick
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun AirportExercise(
    viewModel: AirportViewModel,
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    AirportExercise(
        state = viewState.value,
    ) { action ->
        when (action) {
            is AirportActions.Back -> onBackClick()
            is AirportActions.Repeat -> onRepeatExerciseClick()
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun AirportExercise(
    state: AirportViewState,
    actioner: (AirportActions) -> Unit,
) {
    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(AirportActions.Back) },
            onRepeatExercise = { actioner(AirportActions.Repeat) }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(AirportActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        modifier = Modifier.align(Alignment.TopCenter),
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(
                                exerciseName = ExerciseName.AIRPORT,
                                airportTaskVariant = state.plane.taskVariant
                            )
                        ),
                        timeProgress = state.timerState.timeUntilFinishedProgress,
                        onBack = { actioner(AirportActions.Back) }
                    )
                }
            }

            Icon(
                imageVector = state.plane.icon,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(120.dp)
                    .rotate(state.plane.direction.degree),
                tint = state.plane.color,
            )

            BottomButtons { actioner(AirportActions.Chose(it)) }
        }
    }
}

@Composable
private fun BoxScope.BottomButtons(onClick: (Direction) -> Unit) {
    Box(
        modifier = Modifier.Companion
            .align(Alignment.BottomCenter)
            .size(150.dp)
            .padding(bottom = 16.dp)
    ) {
        IconButton(
            onClick = { onClick(Direction.NORTH) },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Default.North,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }

        IconButton(
            onClick = { onClick(Direction.SOUTH) },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = Icons.Default.South,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }


        IconButton(
            onClick = { onClick(Direction.WEST) },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.West,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }

        IconButton(
            onClick = { onClick(Direction.EAST) },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.East,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        AirportExercise(state = AirportViewState.Test, actioner = {})
    }
}