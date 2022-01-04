package yaroslavgorbach.reaction.feature.exercise.extraNumber.model

import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.utill.TimerCountDown

data class ExtraNumberViewState(
    val numberPacks: List<NumberPack> = listOf(NumberPack.Empty),
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val finishExerciseState: FinishExerciseState = FinishExerciseState(name = ExerciseName.EXTRA_NUMBER)
) {
    companion object {
        val Empty = ExtraNumberViewState()
    }
}
