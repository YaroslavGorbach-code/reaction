package yaroslavgorbach.reaction.feature.exercise.result.model

import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack
import yaroslavgorbach.reaction.utill.TimerCountDown

data class ExerciseResultViewState(
    val exerciseResultUi: ExerciseResultUi
) {
    companion object {
        val Empty = ExerciseResultViewState(exerciseResultUi = ExerciseResultUi.Test)
    }
}
