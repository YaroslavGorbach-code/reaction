package yaroslavgorbach.reaction.feature.exercise.rotation.model

import yaroslavgorbach.reaction.data.exercise.rotation.model.Tables
import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.utill.TimerCountDown

data class RotationViewState(
    val tables: Tables = Tables.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val finishExerciseState: FinishExerciseState = FinishExerciseState(name = ExerciseName.NUMBERS_AND_LETTERS)
) {
    companion object {
        val Test = RotationViewState()
    }
}
