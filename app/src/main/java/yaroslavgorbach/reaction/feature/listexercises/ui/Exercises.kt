package yaroslavgorbach.reaction.feature.listexercises.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.listexercises.model.ExerciseUi
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesViewState
import yaroslavgorbach.reaction.feature.listexercises.presentation.ExercisesViewModel

@Composable
fun Exercises(
    openDetails: (exerciseName: ExerciseName) -> Unit,
) {
    Exercises(
        viewModel = viewModel(),
        openDetails = openDetails
    )
}

@Composable
internal fun Exercises(
    viewModel: ExercisesViewModel,
    openDetails: (exerciseName: ExerciseName) -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    Exercises(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExercisesActions.OpenDetails -> openDetails(action.exerciseName)
            else -> viewModel.submitAction(action)
        }
    }
}

@Composable
internal fun Exercises(
    state: ExercisesViewState,
    actioner: (ExercisesActions) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        state.exercises.forEach { exercise ->
            item {
                ExerciseItem(exercise = exercise) { name ->
                    actioner(ExercisesActions.OpenDetails(name))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme() {
        Exercises(
            state = ExercisesViewState(
                exercises = listOf(
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty,
                    ExerciseUi.Empty
                ),
            )
        ) {

        }
    }
}