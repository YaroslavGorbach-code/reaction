package yaroslavgorbach.reaction.feature.exercise.extranumber.model

import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.Number

sealed class ExtraNumberActions {
    class NumberClick(val number: Number) : ExtraNumberActions()
    object Back : ExtraNumberActions()
    object Repeat : ExtraNumberActions()
    object FinishExercise : ExtraNumberActions()
}