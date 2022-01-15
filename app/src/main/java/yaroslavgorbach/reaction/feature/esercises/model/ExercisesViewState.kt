package yaroslavgorbach.reaction.feature.esercises.model

import yaroslavgorbach.reaction.data.exercises.local.model.Exercise

data class ExercisesViewState(
    val exercises: List<Exercise> = emptyList(),
    val isExerciseAvailableDialogShown: Boolean = false,
    val isOnboardingDialogShown: Boolean = false
) {
    companion object {
        val Empty = ExercisesViewState()
    }
}
