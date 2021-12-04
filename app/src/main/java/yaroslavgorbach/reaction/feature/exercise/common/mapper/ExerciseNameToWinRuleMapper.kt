package yaroslavgorbach.reaction.feature.exercise.common.mapper

import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.WinRule
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberWinRule
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordWinRule

object ExerciseNameToWinRuleMapper {
    fun map(exerciseName: ExerciseName): WinRule {
        return when (exerciseName) {
            ExerciseName.EXTRA_NUMBER -> ExtraNumberWinRule
            ExerciseName.EXTRA_WORD -> ExtraWordWinRule
            ExerciseName.NO_NAME -> WinRule
        }
    }
}