package yaroslavgorbach.reaction.data.listexercises.local.model

data class Exercise(val exerciseName: ExerciseName, val descriptionRes: Int) {
    companion object {
        val Test = Exercise(ExerciseName.TEST, 0)
    }
}
