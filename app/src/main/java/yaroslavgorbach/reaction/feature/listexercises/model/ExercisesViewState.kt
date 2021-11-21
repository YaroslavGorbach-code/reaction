package yaroslavgorbach.reaction.feature.listexercises.model

data class ExercisesViewState(
    val exercises: List<ExerciseUi> = emptyList()
) {
    companion object {
        val Empty = ExercisesViewState()
    }
}
