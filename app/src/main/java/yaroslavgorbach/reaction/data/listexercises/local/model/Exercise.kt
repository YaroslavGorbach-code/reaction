package yaroslavgorbach.reaction.data.listexercises.local.model

import yaroslavgorbach.reaction.R

data class Exercise(
    val name: ExerciseName,
    val descriptionRes: Int,
    val instructionRes: Int,
    val levelComplexity: Int,
    val benefitsArrayRes: Int
) {
    companion object {
        val Empty = Exercise(name = ExerciseName.NO_NAME, R.string.empty, R.string.empty, R.string.empty, R.string.empty)
    }
}
