package yaroslavgorbach.reaction.feature.exercise.common.mapper

import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

object ExerciseNameToInstructionResMapper {
    fun map(exerciseName: ExerciseName): Int {
        return when (exerciseName) {
            ExerciseName.TEST -> R.string.test_instruction
        }
    }
}