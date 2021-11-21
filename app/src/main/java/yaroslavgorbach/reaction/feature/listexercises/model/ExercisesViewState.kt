package yaroslavgorbach.reaction.feature.listexercises.model

data class ExercisesViewState(
    val exercises: List<ExerciseUi> = emptyList(),
    val trainingUi: TrainingUi = TrainingUi.Test
) {
    companion object {
        val Empty = ExercisesViewState()
    }
}
