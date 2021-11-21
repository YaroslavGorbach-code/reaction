package yaroslavgorbach.reaction.data.listexercises.local.model

data class Exercise(val exerciseName: ExerciseName) {
    companion object {
        val Test = Exercise(ExerciseName.TEST)
    }
}
