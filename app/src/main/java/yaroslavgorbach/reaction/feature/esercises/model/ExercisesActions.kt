package yaroslavgorbach.reaction.feature.esercises.model

import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName

sealed class ExercisesActions {
    data class OpenDetails(val exerciseName: ExerciseName) : ExercisesActions()
    data class ShowExerciseIsNotAvailableDialog(val exerciseName: ExerciseName) : ExercisesActions()
    object HideExerciseIsNotAvailableDialog : ExercisesActions()
}