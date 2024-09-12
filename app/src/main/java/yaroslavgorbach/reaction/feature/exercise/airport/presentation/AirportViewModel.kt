package yaroslavgorbach.reaction.feature.exercise.airport.presentation

import android.app.Activity
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.domain.exercise.ObservePlainsInteractor
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.airport.model.Direction
import yaroslavgorbach.reaction.data.exercise.airport.model.Plane
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportActions
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportUiMessage
import yaroslavgorbach.reaction.feature.exercise.airport.model.AirportViewState
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.utill.*
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    observePlainsInteractor: ObservePlainsInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    saveStatisticsInteractor: SaveStatisticsInteractor,
    addManager: AdManager
) : BaseExerciseViewModel(
    exerciseName = ExerciseName.AIRPORT,
    getExerciseInteractor,
    saveStatisticsInteractor,
    addManager
) {

    private val pendingActions = MutableSharedFlow<AirportActions>()

    private val items: MutableStateFlow<List<Plane>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<AirportUiMessage> = UiMessageManager()

    val state: StateFlow<AirportViewState> = combine(
        items,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished,
        uiMessageManager.message,
        averageTimeForAnswer
    ) { itemPacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished, message, averageTimeForAnswer ->
        AirportViewState(
            plane = itemPacks.firstOr(Plane.Test),
            timerState = timerState,
            finishExerciseState = FinishExerciseState(
                name = exerciseName,
                isFinished = isExerciseFinished,
                pointsCorrect = pointsCorrect,
                pointsIncorrect = pointsIncorrect,
                averageTimeForAnswer = averageTimeForAnswer
            ),
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = AirportViewState.Empty
    )

    init {
        viewModelScope.launch {
            observePlainsInteractor().flowOn(Dispatchers.IO).collect(items::emit)

            pendingActions.collect { action ->
                when (action) {
                    is AirportActions.Chose -> onItemClick(action.direction)
                    is AirportActions.FinishExercise -> finishExercise(
                        state.value.finishExerciseState.isWin,
                        action.activity
                    )
                    else -> error("$action is not handled")
                }
            }
        }
    }

    override suspend fun finishExercise(isSuccess: Boolean, activity: Activity) {
        super.finishExercise(isSuccess, activity)
        updateExercise()
    }

    private suspend fun updateExercise() {
        if (state.value.finishExerciseState.isWin) {
            exercise?.let { updateExerciseInteractor(exercise = it.copy(numberOfWins = it.numberOfWins + 1)) }
        }
    }

    private fun onItemClick(item: Direction) {
        onAnswer()

        viewModelScope.launch {
            val currentItem = items.first().first()

            if (currentItem.checkIsResultCorrect(item)) {
                pointsCorrect.emit(pointsCorrect.value + 1)
                uiMessageManager.emitMessage(UiMessage(AirportUiMessage.AnswerIsCorrect))
            } else {
                pointsInCorrect.emit(pointsInCorrect.value + 1)
                uiMessageManager.emitMessage(UiMessage(AirportUiMessage.AnswerIsNotCorrect))
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
