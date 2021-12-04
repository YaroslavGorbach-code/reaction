package yaroslavgorbach.reaction.feature.exercise.extraWord.model

import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.utill.TimerCountDown

data class ExtraWordViewState(
    val wordPacks: List<WordPack> = listOf(WordPack.Empty),
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Empty = ExtraWordViewState()
    }
}
