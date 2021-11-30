package yaroslavgorbach.reaction.feature.exercise.common.mapper

import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.WinRule
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberWinRule

object ExerciseNameToWinRuleMapper {
    fun map(exerciseName: ExerciseName): WinRule {
        return when (exerciseName) {
            ExerciseName.TEST -> ExtraNumberWinRule
        }
    }
}