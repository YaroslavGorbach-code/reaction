package yaroslavgorbach.reaction.feature.exercise.extraNumber.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveExtraNumbersInteractor
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.Number
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberActions
import yaroslavgorbach.reaction.feature.exercise.extraNumber.model.ExtraNumberViewState
import javax.inject.Inject

@HiltViewModel
class ExtraNumberViewModel @Inject constructor(
    observeExtraNumbersInteractor: ObserveExtraNumbersInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<ExtraNumberActions>()

    private val numberPacks: MutableStateFlow<List<NumberPack>> = MutableStateFlow(emptyList())

    val state: StateFlow<ExtraNumberViewState> = combine(
        numberPacks,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { numberPacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        ExtraNumberViewState(
            numberPacks = numberPacks,
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ExtraNumberViewState.Empty
    )

    init {
        timerCountDown.start()

        viewModelScope.launch {
            observeExtraNumbersInteractor()
                .flowOn(Dispatchers.IO)
                .collect(numberPacks::emit)

            pendingActions.collect { action ->
                when (action) {
                    is ExtraNumberActions.NumberClick -> onNumberClick(action.number)
                    is ExtraNumberActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onNumberClick(number: Number) {
        viewModelScope.launch {
            if (number.isExtra) {
                pointsCorrect.emit(pointsCorrect.value + 1)
            } else {
                pointsInCorrect.emit(pointsInCorrect.value + 1)
            }

            numberPacks.emit(numberPacks.value.drop(1))
        }
    }

    fun submitAction(action: ExtraNumberActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}
