package yaroslavgorbach.reaction.feature.exercise.airport.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObservePlainsInteractor
import yaroslavgorbach.reaction.data.exercise.airport.model.Direction
import yaroslavgorbach.reaction.data.exercise.airport.model.Plane
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportActions
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportViewState
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.utill.firstOr
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    observePlainsInteractor: ObservePlainsInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<AirportActions>()

    private val items: MutableStateFlow<List<Plane>> = MutableStateFlow(emptyList())

    val state: StateFlow<AirportViewState> = combine(
        items,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { itemPacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        AirportViewState(
            plane = itemPacks.firstOr(Plane.Test),
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = AirportViewState.Empty
    )

    init {
        viewModelScope.launch {
            observePlainsInteractor()
                .flowOn(Dispatchers.IO)
                .collect(items::emit)

            pendingActions.collect { action ->
                when (action) {
                    is AirportActions.Chose -> onItemClick(action.direction)
                    is AirportActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onItemClick(item: Direction) {
        viewModelScope.launch {
            val currentItem = items.first().first()

            if (currentItem.checkIsResultCorrect(item)){
                pointsCorrect.emit(pointsCorrect.value + 1)
            }else{
                pointsInCorrect.emit(pointsInCorrect.value + 1)
            }

            items.emit(items.value.drop(1))
        }
    }

    fun submitAction(action: AirportActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}
