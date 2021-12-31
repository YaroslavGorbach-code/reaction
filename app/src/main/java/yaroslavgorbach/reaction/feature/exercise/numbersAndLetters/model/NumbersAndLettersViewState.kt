package yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model

import yaroslavgorbach.reaction.data.exercise.numbersLetters.model.NumberAndLetter
import yaroslavgorbach.reaction.utill.TimerCountDown

data class NumbersAndLettersViewState(
    val numberAndLetter: NumberAndLetter = NumberAndLetter.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Test = NumbersAndLettersViewState()
    }
}
