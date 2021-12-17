package yaroslavgorbach.reaction.feature.exercise.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlActions
import yaroslavgorbach.reaction.utill.TimerCountDown

abstract class BaseExerciseViewModel : ViewModel() {

    protected val timerCountDown: TimerCountDown =
        TimerCountDown(
            coroutineScope = viewModelScope,
            millisInFuture = TimerCountDown.ONE_MINUTE,
            countDownInterval = 100
        )

    protected val pointsCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    protected val pointsInCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    protected val isExerciseFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

    protected fun onFinishExercise() {
        viewModelScope.launch {
            isExerciseFinished.emit(true)
        }
    }

    init {
        timerCountDown.start()
    }

}