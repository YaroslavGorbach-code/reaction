package yaroslavgorbach.reaction.data.listExercises.local.model

import yaroslavgorbach.reaction.R

data class Exercise(
    val name: ExerciseName,
    val descriptionRes: Int,
    val instructionRes: Int,
    val levelComplexity: Int,
    val benefitsArrayRes: Int
) {
    companion object {
        val Empty = Exercise(
            name = ExerciseName.NO_NAME,
            R.string.empty,
            R.string.empty,
            R.string.empty,
            R.string.empty
        )
        val Test = Exercise(
            name = ExerciseName.ROTATION,
            R.string.description_rotation,
            R.string.instruction_rotation,
            R.string.level_complexity,
            R.array.benefits_face_control
        )
    }
}
