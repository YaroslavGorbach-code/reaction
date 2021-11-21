package yaroslavgorbach.reaction.feature.listexercises.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.listexercises.model.ExerciseUi
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesViewState
import yaroslavgorbach.reaction.feature.listexercises.presentation.ExercisesViewModel

@ExperimentalMaterialApi
@Composable
fun Exercises(
    openDetails: (exerciseName: ExerciseName) -> Unit,
    openTraining: () -> Unit,
    openSettings: () -> Unit,
) {
    Exercises(
        viewModel = viewModel(),
        openDetails = openDetails,
        openTraining = openTraining,
        openSettings = openSettings,
    )
}

@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    viewModel: ExercisesViewModel,
    openDetails: (exerciseName: ExerciseName) -> Unit,
    openTraining: () -> Unit,
    openSettings: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    Exercises(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExercisesActions.OpenDetails -> openDetails(action.exerciseName)
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

        TrainingSurface(
            training = state.trainingUi,
            onTraining = { actioner(ExercisesActions.OpenTraining) },
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        )

        LazyColumn {
            items(state.exercises) { exercise ->
                ExerciseItem(exercise = exercise) { name ->
                    actioner(ExercisesActions.OpenDetails(name))
                }
            }
        }
    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme() {
        Exercises(
            state = ExercisesViewState(
                exercises = listOf(
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test,
                    ExerciseUi.Test
                ),
            )
        ) {

        }
    }
}