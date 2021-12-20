package yaroslavgorbach.reaction.feature.exercise.common.mapper

import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName
import kotlin.random.Random

object ExerciseNameToInstructionResMapper {
    fun map(exerciseName: ExerciseName, complexSortIsSimilar: Boolean = false): Int {
        return when (exerciseName) {
            ExerciseName.EXTRA_NUMBER -> R.string.instruction_extra_number
            ExerciseName.EXTRA_WORD -> R.string.instruction_extra_word
            ExerciseName.FACE_CONTROL -> R.string.instruction_face_control
            ExerciseName.COMPLEX_SORT -> {
                if (complexSortIsSimilar) {
                    R.string.instruction_sort_item_similar
                } else {
                    R.string.instruction_sort_item_not_similar
                }
            }
            ExerciseName.NO_NAME -> 0
        }
    }
}