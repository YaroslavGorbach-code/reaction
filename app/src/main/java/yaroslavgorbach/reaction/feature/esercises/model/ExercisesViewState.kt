package yaroslavgorbach.reaction.feature.esercises.model

import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.training.model.Training

data class ExercisesViewState(
    val exercises: List<Exercise> = emptyList(),
    val isExerciseAvailableDialogShown: Boolean = false
) {
    companion object {
        val Empty = ExercisesViewState()
    }
}
