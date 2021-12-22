package yaroslavgorbach.reaction.feature.exercise.stroop.model

import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.utill.TimerCountDown

data class StroopViewState(
    val word: StroopWord = StroopWord.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Empty = StroopViewState()
        val Test = StroopViewState()
    }
}
