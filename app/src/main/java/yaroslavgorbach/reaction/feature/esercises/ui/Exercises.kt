package yaroslavgorbach.reaction.feature.esercises.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import yaroslavgorbach.reaction.feature.esercises.presentation.ExercisesViewModel

@ExperimentalMaterialApi
@Composable
fun Exercises(
    openDescription: (exerciseName: ExerciseName) -> Unit,
) {
    Exercises(
        viewModel = hiltViewModel(),
        openDescription = openDescription,
    )
}

@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    viewModel: ExercisesViewModel,
    openDescription: (exerciseName: ExerciseName) -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    Exercises(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExercisesActions.OpenDetails -> openDescription(action.exerciseName)
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    state: ExercisesViewState,
    actioner: (ExercisesActions) -> Unit,
) {
    if (state.isExerciseAvailableDialogShown) {
        ShowExerciseAvailableDialog {
            actioner(ExercisesActions.HideExerciseIsNotAvailableDialog)
        }
    }

    if (state.isOnboardingDialogShown) {
        ShowOnboardingDialog {
            actioner(ExercisesActions.HideOnboardingDialog)
        }
    }

    LazyColumn {
        item {
            Text(
                text = stringResource(id = R.string.exercises),
                style = MaterialTheme.typography.caption,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.motivation_text),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        items(state.exercises) { exercise ->
            ExerciseItem(exercise = exercise) { isAvailable ->
                if (isAvailable) {
                    actioner(ExercisesActions.OpenDetails(exerciseName = exercise.name))
                } else {
                    actioner(ExercisesActions.ShowExerciseIsNotAvailableDialog(exerciseName = exercise.name))
                }
            }
        }
    }
}

@Composable
private fun ShowExerciseAvailableDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.exercise_is_unavailable)) },
        text = { Text(stringResource(id = R.string.exercise_is_unavailable_explanation)) },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.medium
                        ),
                    onClick = onDismiss
                ) {
                    Text(stringResource(id = R.string.good))
                }
            }
        }
    )
}

@Composable
private fun ShowOnboardingDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.about_app)) },
        text = { Text(stringResource(id = R.string.onboarding_text)) },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.medium
                        ),
                    onClick = onDismiss
                ) {
                    Text(stringResource(id = R.string.start))
                }
            }
        }
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        Exercises(
            state = ExercisesViewState(
                exercises = listOf(Exercise.Empty),
            )
        ) {

        }
    }
}