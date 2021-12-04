package yaroslavgorbach.reaction.feature.listexercises.model

import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName

sealed class ExercisesActions {
    data class OpenDetails(val exerciseName: ExerciseName) : ExercisesActions()
    object OpenTraining : ExercisesActions()
    object OpenSettings : ExercisesActions()
}