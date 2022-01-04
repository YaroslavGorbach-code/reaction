package yaroslavgorbach.reaction.feature.esercises.model

import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName

sealed class ExercisesActions {
    data class OpenDetails(val exerciseName: ExerciseName) : ExercisesActions()
    object OpenTraining : ExercisesActions()
    object OpenSettings : ExercisesActions()
}