package yaroslavgorbach.reaction.feature.listexercises.model

import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.training.model.Training

data class ExercisesViewState(
    val exercises: List<Exercise> = emptyList(),
    val trainings: List<Training> = emptyList()
) {
    companion object {
        val Empty = ExercisesViewState()
    }
}
