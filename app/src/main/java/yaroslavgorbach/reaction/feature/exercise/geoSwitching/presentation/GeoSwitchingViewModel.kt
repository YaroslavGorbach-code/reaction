package yaroslavgorbach.reaction.feature.exercise.geoSwitching.presentation

import android.app.Activity
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.domain.exercise.ObserveFiguresInteractor
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.geoSwitching.model.GeoFigure
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingActions
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingUiMessage
import yaroslavgorbach.reaction.feature.exercise.geoSwitching.model.GeoSwitchingViewState
import yaroslavgorbach.reaction.utill.*
import javax.inject.Inject

@HiltViewModel
class GeoSwitchingViewModel @Inject constructor(
    observeGeoFiguresInteractor: ObserveFiguresInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
    saveStatisticsInteractor: SaveStatisticsInteractor,
    addManager: AdManager
) : BaseExerciseViewModel(
    exerciseName = ExerciseName.GEO_SWITCHING,
    getExerciseInteractor,
    saveStatisticsInteractor,
    addManager
) {

    private val pendingActions = MutableSharedFlow<GeoSwitchingActions>()

    private val items: MutableStateFlow<List<GeoFigure>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<GeoSwitchingUiMessage> = UiMessageManager()

    val state: StateFlow<GeoSwitchingViewState> = combine(
        items,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished,
        uiMessageManager.message,
        averageTimeForAnswer
    ) { figures, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished, message, averageTimeForAnswer ->
        GeoSwitchingViewState(
            figure = figures.firstOr(GeoFigure.Test),
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
        initialValue = GeoSwitchingViewState.Test
    )

    init {
        viewModelScope.launch {
            observeGeoFiguresInteractor()
                .flowOn(Dispatchers.IO)
                .collect(items::emit)

            pendingActions.collect { action ->
                when (action) {
                    is GeoSwitchingActions.Chose -> onChose(action.yesNoChose)
                    is GeoSwitchingActions.FinishExercise -> finishExercise(
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

    private fun onChose(variant: YesNoChoseVariations) {
        onAnswer()

        viewModelScope.launch {
            val currentItem = items.first().first()

            currentItem.checkAnswer(variant) { isCorrect ->
                if (isCorrect) {
                    pointsCorrect.emit(pointsCorrect.value + 1)
                    uiMessageManager.emitMessage(UiMessage(GeoSwitchingUiMessage.AnswerIsCorrect))
                } else {
                    pointsInCorrect.emit(pointsInCorrect.value + 1)
                    uiMessageManager.emitMessage(UiMessage(GeoSwitchingUiMessage.AnswerIsNotCorrect))
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
