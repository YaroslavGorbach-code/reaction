package yaroslavgorbach.reaction.feature.exercise.faceControl.model

import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.utill.TimerCountDown

data class FaceControlViewState(
    val facePacks: List<FacePack> = listOf(FacePack.Empty),
    val timerState: TimerCountDown.TimerState = TimerCountDown.TimerState.Tick(0, "00:00", 0f),
    val pointsCorrect: Int = 0,
    val pointsIncorrect: Int = 0,
    val isFinished: Boolean = false
) {
    companion object {
        val Empty = FaceControlViewState()
    }
}
