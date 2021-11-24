package yaroslavgorbach.reaction.feature.exercise.extranumber.model

import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack
import yaroslavgorbach.reaction.utill.TimerCountDown

data class ExtraNumberViewState(
    val numberPacks: List<NumberPack> = emptyList(),
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f)
) {
    companion object {
        val Empty = ExtraNumberViewState()
    }
}
