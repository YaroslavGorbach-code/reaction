package yaroslavgorbach.reaction.feature.exercise.rotation.model

import yaroslavgorbach.reaction.data.exercise.rotation.model.Tables
import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.utill.TimerCountDown

data class RotationViewState(
    val tables: Tables = Tables.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Test = RotationViewState()
    }
}
