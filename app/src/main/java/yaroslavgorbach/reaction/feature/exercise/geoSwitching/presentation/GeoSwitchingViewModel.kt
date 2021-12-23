package yaroslavgorbach.reaction.feature.exercise.geoSwitching.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveFiguresInteractor
import yaroslavgorbach.reaction.data.exercise.geoSwitching.model.GeoFigure
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.ChoseVariations
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingActions
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingViewState
import yaroslavgorbach.reaction.utill.firstOr
import javax.inject.Inject

@HiltViewModel
class GeoSwitchingViewModel @Inject constructor(
    observeGeoFiguresInteractor: ObserveFiguresInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<GeoSwitchingActions>()

    private val items: MutableStateFlow<List<GeoFigure>> = MutableStateFlow(emptyList())

    val state: StateFlow<GeoSwitchingViewState> = combine(
        items,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { figures, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        GeoSwitchingViewState(
            figure = figures.firstOr(GeoFigure.Test),
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = GeoSwitchingViewState.Test
    )

    init {
        viewModelScope.launch {
            observeGeoFiguresInteractor()
                .flowOn(Dispatchers.IO)
                .collect(items::emit)

            pendingActions.collect { action ->
                when (action) {
                    is GeoSwitchingActions.Chose -> onChose(action.chose)
                    is GeoSwitchingActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onChose(variant: ChoseVariations) {
        viewModelScope.launch {
            val currentItem = items.first().first()

            currentItem.checkAnswer(variant) { isCorrect ->
                if (isCorrect){
                    pointsCorrect.emit(pointsCorrect.value + 1)
                }else{
                    pointsInCorrect.emit(pointsInCorrect.value + 1)

                }
            }
            items.emit(items.value.drop(1))
        }
    }

    fun submitAction(action: GeoSwitchingActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}
