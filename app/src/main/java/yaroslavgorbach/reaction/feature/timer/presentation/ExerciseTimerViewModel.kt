package yaroslavgorbach.reaction.feature.timer.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.EXERCISE_NAME_ARG
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.timer.model.ExerciseTimerActions
import yaroslavgorbach.reaction.feature.timer.model.ExerciseTimerViewState
import yaroslavgorbach.reaction.utill.TimerCountDown
import yaroslavgorbach.reaction.utill.TimerCountDown.Companion.ONE_SECOND
import javax.inject.Inject

@HiltViewModel
class ExerciseTimerViewModel @Inject constructor(savedState: SavedStateHandle) : ViewModel() {

    val exerciseName: ExerciseName = savedState[EXERCISE_NAME_ARG]!!

    private val pendingActions = MutableSharedFlow<ExerciseTimerActions>()

    val timerCountDown: TimerCountDown =
        TimerCountDown(
            coroutineScope = viewModelScope,
            millisInFuture = ONE_SECOND * 4,
            countDownInterval = 1000
        )

    val state: StateFlow<ExerciseTimerViewState> = combine(timerCountDown.state) { timer ->
        ExerciseTimerViewState(exerciseName = exerciseName, timerState = timer[0])
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ExerciseTimerViewState.Empty
    )

    init {
        viewModelScope.launch {
            delay(100)
            timerCountDown.start()
        }

        viewModelScope.launch {
            pendingActions.collect { action ->

                when (action) {
                    else -> error("action is not handled")
                }
            }
        }
    }

    fun submitAction(action: ExerciseTimerActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}