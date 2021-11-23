package yaroslavgorbach.reaction.feature.exercise.extranumber.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberActions
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberViewState
import yaroslavgorbach.reaction.feature.exercise.extranumber.presentation.ExtraNumberViewModel
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesViewState

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

@ExperimentalMaterialApi
@Composable
internal fun ExtraNumbers(
    viewModel: ExtraNumberViewModel,
    onBackClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    ExtraNumbers(
        state = viewState.value,
    ) { action ->
        when (action) {
            is ExtraNumberActions.OnBackAction -> onBackClick()
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalMaterialApi
@Composable
internal fun ExtraNumbers(
    state: ExtraNumberViewState,
    actioner: (ExtraNumberActions) -> Unit,
) {

}


@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        ExtraNumbers(state = ExtraNumberViewState(numberPacks = listOf(NumberPack.Test)), actioner = {})
    }
}