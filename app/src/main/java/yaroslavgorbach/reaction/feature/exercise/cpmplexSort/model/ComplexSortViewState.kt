package yaroslavgorbach.reaction.feature.exercise.cpmplexSort.model

import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem
import yaroslavgorbach.reaction.utill.TimerCountDown

data class ComplexSortViewState(
    val items: List<ComplexSortItem> = emptyList(),
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Empty = ComplexSortViewState()
        val Test = ComplexSortViewState(items = listOf(ComplexSortItem.Test))
    }
}
