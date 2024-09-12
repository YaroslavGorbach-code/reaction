package yaroslavgorbach.reaction.feature.exercise.rotation.presentation

import android.app.Activity
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.domain.exercise.ObserveTablesInteractor
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.rotation.model.Tables
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationActions
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationUiMessage
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationViewState
import yaroslavgorbach.reaction.utill.*
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class RotationViewModel @Inject constructor(
    observeTablesInteractor: ObserveTablesInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
    saveStatisticsInteractor: SaveStatisticsInteractor,
    addManager: AdManager
) : BaseExerciseViewModel(
    exerciseName = ExerciseName.ROTATION,
    getExerciseInteractor,
    saveStatisticsInteractor,
    addManager
) {

    private val pendingActions = MutableSharedFlow<RotationActions>()

    private val tables: MutableStateFlow<List<Tables>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<RotationUiMessage> = UiMessageManager()

    val state: StateFlow<RotationViewState> = combine(
        tables,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished,
        uiMessageManager.message,
        averageTimeForAnswer
    ) { tables, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished, message, averageTimeForAnswer ->
        RotationViewState(
            tables = tables.firstOr(Tables.Test),
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
        initialValue = RotationViewState.Test
    )

    init {
        timerCountDown.start()

        viewModelScope.launch {
            observeTablesInteractor()
                .flowOn(Dispatchers.IO)
                .collect(tables::emit)

            pendingActions.collect { action ->
                when (action) {
                    is RotationActions.OnChose -> checkChosenValiant(action.chose)
                    is RotationActions.FinishExercise -> finishExercise(
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

    private fun checkChosenValiant(choseVariant: YesNoChoseVariations) {
        onAnswer()

        viewModelScope.launch {
            val currentTables = state.value.tables

            currentTables.checkAnswer(choseVariant) { isCorrect ->
                if (isCorrect) {
                    pointsCorrect.emit(pointsCorrect.value + 1)
                    uiMessageManager.emitMessage(UiMessage(message = RotationUiMessage.AnswerIsCorrect))
                } else {
                    pointsInCorrect.emit(pointsInCorrect.value + 1)
                    uiMessageManager.emitMessage(UiMessage(message = RotationUiMessage.AnswerIsNotCorrect))
                }
            }

            tables.emit(tables.value.drop(1))
        }
    }

    fun submitAction(action: RotationActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}


