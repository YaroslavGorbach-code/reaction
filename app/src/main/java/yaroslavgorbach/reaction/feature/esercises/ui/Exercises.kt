package yaroslavgorbach.reaction.feature.esercises.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import yaroslavgorbach.reaction.feature.esercises.presentation.ExercisesViewModel

@ExperimentalMaterialApi
@Composable
fun Exercises(
    openDescription: (exerciseName: ExerciseName) -> Unit,
    openTraining: () -> Unit,
    openSettings: () -> Unit,
) {
    Exercises(
        viewModel = hiltViewModel(),
        openDescription = openDescription,
        openTraining = openTraining,
        openSettings = openSettings,
    )
}

@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    viewModel: ExercisesViewModel,
    openDescription: (exerciseName: ExerciseName) -> Unit,
    openTraining: () -> Unit,
    openSettings: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    Exercises(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExercisesActions.OpenDetails -> openDescription(action.exerciseName)

            is ExercisesActions.OpenTraining -> openTraining()
            is ExercisesActions.OpenSettings -> openSettings()
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
    Column {
        Icon(
            Icons.Outlined.Settings,
            contentDescription = "Settings",
            modifier = Modifier
                .align(End)
                .padding(end = 8.dp)
                .clickable { actioner(ExercisesActions.OpenSettings) })

        LazyColumn {
             item {
                 Text(
                     text = "Упражнения",
                     style = MaterialTheme.typography.caption,
                     fontSize = 30.sp,
                     modifier = Modifier.padding(start = 8.dp)
                 )

                 Text(
                     text = "Практикуйтесь ежидневно и результат не заставит себя долго ждать",
                     style = MaterialTheme.typography.subtitle1,
                     fontSize = 16.sp,
                     modifier = Modifier.padding(start = 8.dp)
                 )

             }
            items(state.exercises) { exercise ->
                ExerciseItem(exercise = exercise) {
                    actioner(ExercisesActions.OpenDetails(exerciseName = exercise.name))
                }
            }
        }
    }

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