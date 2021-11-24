package yaroslavgorbach.reaction.feature.exercise.result.model

import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.Number
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

sealed class ExerciseResultActions {
    object OnBackAction : ExerciseResultActions()
    class OnStartAgain(exerciseName: ExerciseName) : ExerciseResultActions()
}