package yaroslavgorbach.reaction.feature.esercises.model

import android.app.Activity
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName

sealed class ExercisesActions {
    data class OpenExerciseStartTimer(val exerciseName: ExerciseName) : ExercisesActions()
    data class ShowExerciseIsNotAvailableDialog(val exerciseName: ExerciseName) : ExercisesActions()
    object HideExerciseIsNotAvailableDialog : ExercisesActions()
    object HideOnboardingDialog : ExercisesActions()
    data class ShowRewordAd(val activity: Activity, val exerciseName: ExerciseName) : ExercisesActions()
    data class RequestShowRewordAd(val exerciseName: ExerciseName) : ExercisesActions()
}