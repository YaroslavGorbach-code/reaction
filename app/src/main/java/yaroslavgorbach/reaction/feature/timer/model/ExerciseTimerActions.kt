package yaroslavgorbach.reaction.feature.timer.model

import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName

sealed class ExerciseTimerActions {
    data class OpenExercise(val exerciseName: ExerciseName) : ExerciseTimerActions()
}