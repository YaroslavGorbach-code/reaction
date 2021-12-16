package yaroslavgorbach.reaction.feature.exercise.cpmplexSort.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveComplexSortItemsInteractor
import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.cpmplexSort.model.ComplexSortActions
import yaroslavgorbach.reaction.feature.exercise.cpmplexSort.model.ComplexSortViewState
import javax.inject.Inject

@HiltViewModel
class ComplexSortViewModel @Inject constructor(
    observeComplexSortItemPacksItemsInteractor: ObserveComplexSortItemsInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<ComplexSortActions>()

    private val items: MutableStateFlow<List<ComplexSortItem>> = MutableStateFlow(emptyList())

    val state: StateFlow<ComplexSortViewState> = combine(
        items,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { itemPacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        ComplexSortViewState(
            items = itemPacks,
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ComplexSortViewState.Empty
    )

    init {
        viewModelScope.launch {
            observeComplexSortItemPacksItemsInteractor()
                .flowOn(Dispatchers.IO)
                .collect(items::emit)

            pendingActions.collect { action ->
                when (action) {
                    is ComplexSortActions.ItemClick -> onItemClick(action.item)
                    is ComplexSortActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onItemClick(item: ComplexSortItem) {
        viewModelScope.launch {
            val currentItem = items.first().first()

            when (currentItem.isSimilar) {
                true -> {
                    if (currentItem.color == item.color && currentItem.icon == item.icon) {
                        pointsCorrect.emit(pointsCorrect.value + 1)
                    } else {
                        pointsInCorrect.emit(pointsInCorrect.value + 1)
                    }
                }

                false -> {
                    if (currentItem.color != item.color && currentItem.icon != item.icon) {
                        pointsCorrect.emit(pointsCorrect.value + 1)
                    } else {
                        pointsInCorrect.emit(pointsInCorrect.value + 1)
                    }
                }
            }

            items.emit(items.value.drop(1))
        }
    }

    fun submitAction(action: ComplexSortActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}
