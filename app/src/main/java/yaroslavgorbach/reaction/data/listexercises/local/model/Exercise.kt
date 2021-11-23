package yaroslavgorbach.reaction.data.listexercises.local.model

import yaroslavgorbach.reaction.R

data class Exercise(val exerciseName: ExerciseName, val descriptionRes: Int) {
    companion object {
        val Test = Exercise(ExerciseName.TEST, R.string.test_description)
    }
}
