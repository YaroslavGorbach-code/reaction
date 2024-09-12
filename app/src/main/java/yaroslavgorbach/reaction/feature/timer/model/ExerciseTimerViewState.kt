package yaroslavgorbach.reaction.feature.timer.model

import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.utill.TimerCountDown

data class ExerciseTimerViewState(
    val exerciseName: ExerciseName = ExerciseName.NO_NAME,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(3,0, "00:00", 0f),
) {
    companion object {
        val Empty = ExerciseTimerViewState()
    }
}
