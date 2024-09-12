package yaroslavgorbach.reaction.feature.esercises.model

import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName

sealed class ExercisesUiMassage {
    data class ShowRewardAd(val exerciseName: ExerciseName) : ExercisesUiMassage()
    data class ShowStatistics(val exerciseName: ExerciseName) : ExercisesUiMassage()
    data class ShowExerciseIsUnavailable(val exerciseName: ExerciseName) : ExercisesUiMassage()
}