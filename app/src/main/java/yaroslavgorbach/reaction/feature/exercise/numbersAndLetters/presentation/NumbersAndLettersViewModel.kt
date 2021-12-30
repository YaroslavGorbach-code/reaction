package yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveFiguresInteractor
import yaroslavgorbach.reaction.business.exercise.ObserveNumbersAndLettersInteractor
import yaroslavgorbach.reaction.data.exercise.geoSwitching.model.GeoFigure
import yaroslavgorbach.reaction.data.exercise.numbersLetters.model.NumberAndLetter
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingActions
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingViewState
import yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model.NumbersAndLettersActions
import yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model.NumbersAndLettersViewState
import yaroslavgorbach.reaction.utill.firstOr
import javax.inject.Inject

@HiltViewModel
class NumbersAndLettersViewModel @Inject constructor(
    observeNumbersAndLettersInteractor: ObserveNumbersAndLettersInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<NumbersAndLettersActions>()

    private val items: MutableStateFlow<List<NumberAndLetter>> = MutableStateFlow(emptyList())

    val state: StateFlow<NumbersAndLettersViewState> = combine(
        items,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { numbersAndLetters, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        NumbersAndLettersViewState(
            numberAndLetter = numbersAndLetters.firstOr(NumberAndLetter.Test),
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = NumbersAndLettersViewState.Test
    )

    init {
        viewModelScope.launch {
            observeNumbersAndLettersInteractor()
                .flowOn(Dispatchers.IO)
                .collect(items::emit)

            pendingActions.collect { action ->
                when (action) {
                    is NumbersAndLettersActions.Chose -> onChose(action.chose)
                    is NumbersAndLettersActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onChose(variant: YesNoChoseVariations) {
        viewModelScope.launch {
            val currentItem = items.first().first()

            currentItem.checkAnswer(variant) { isCorrect ->
                if (isCorrect) {
                    pointsCorrect.emit(pointsCorrect.value + 1)
                } else {
                    pointsInCorrect.emit(pointsInCorrect.value + 1)
                }
            }

            items.emit(items.value.drop(1))
        }
    }

    fun submitAction(action: NumbersAndLettersActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}
