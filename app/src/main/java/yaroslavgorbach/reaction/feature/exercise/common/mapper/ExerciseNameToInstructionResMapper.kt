package yaroslavgorbach.reaction.feature.exercise.common.mapper

import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName

object ExerciseNameToInstructionResMapper {
    fun map(exerciseName: ExerciseName): Int {
        return when (exerciseName) {
            ExerciseName.EXTRA_NUMBER -> R.string.instruction_extra_number
            ExerciseName.EXTRA_WORD -> R.string.instruction_extra_word
            ExerciseName.FACE_CONTROL -> R.string.instruction_face_control
            ExerciseName.NO_NAME -> 0
        }
    }
}