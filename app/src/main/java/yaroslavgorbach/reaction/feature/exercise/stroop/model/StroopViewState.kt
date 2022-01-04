package yaroslavgorbach.reaction.feature.exercise.stroop.model

import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.utill.TimerCountDown

data class StroopViewState(
    val word: StroopWord = StroopWord.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val finishExerciseState: FinishExerciseState = FinishExerciseState(name = ExerciseName.STROOP)
) {
    companion object {
        val Empty = StroopViewState()
        val Test = StroopViewState()
    }
}
