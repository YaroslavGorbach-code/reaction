package yaroslavgorbach.reaction.feature.exercise.common.mapper

import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.WinRule
import yaroslavgorbach.reaction.feature.exercise.cpmplexSort.model.ComplexSortWinRule
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberWinRule
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordWinRule
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlWinRule

object ExerciseNameToWinRuleMapper {
    fun map(exerciseName: ExerciseName): WinRule {
        return when (exerciseName) {
            ExerciseName.EXTRA_NUMBER -> ExtraNumberWinRule
            ExerciseName.EXTRA_WORD -> ExtraWordWinRule
            ExerciseName.FACE_CONTROL -> FaceControlWinRule
            ExerciseName.COMPLEX_SORT -> ComplexSortWinRule
            ExerciseName.NO_NAME -> WinRule
        }
    }
}