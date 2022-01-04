package yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model

import yaroslavgorbach.reaction.data.exercise.numbersLetters.model.NumberAndLetter
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.utill.TimerCountDown

data class NumbersAndLettersViewState(
    val numberAndLetter: NumberAndLetter = NumberAndLetter.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val finishExerciseState: FinishExerciseState = FinishExerciseState(name = ExerciseName.NUMBERS_AND_LETTERS)
) {
    companion object {
        val Test = NumbersAndLettersViewState()
    }
}
