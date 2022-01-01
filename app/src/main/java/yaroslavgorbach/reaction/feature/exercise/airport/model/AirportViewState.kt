package yaroslavgorbach.reaction.feature.exercise.airport.model

import yaroslavgorbach.reaction.data.exercise.airport.model.Plane
import yaroslavgorbach.reaction.utill.TimerCountDown

data class AirportViewState(
    val plane: Plane = Plane.Test,
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Empty = AirportViewState()
        val Test = AirportViewState(Plane.Test)
    }
}
