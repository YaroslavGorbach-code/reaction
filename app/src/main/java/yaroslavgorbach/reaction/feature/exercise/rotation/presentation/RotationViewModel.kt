package yaroslavgorbach.reaction.feature.exercise.rotation.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveTablesInteractor
import yaroslavgorbach.reaction.business.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.business.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.rotation.model.Tables
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationActions
import yaroslavgorbach.reaction.feature.exercise.rotation.model.RotationViewState
import yaroslavgorbach.reaction.utill.UiMessageManager
import yaroslavgorbach.reaction.utill.firstOr
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class RotationViewModel @Inject constructor(
    observeTablesInteractor: ObserveTablesInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
) : BaseExerciseViewModel(exerciseName = ExerciseName.ROTATION, getExerciseInteractor) {

    private val pendingActions = MutableSharedFlow<RotationActions>()

    private val tables: MutableStateFlow<List<Tables>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<Any> = UiMessageManager()

    val state: StateFlow<RotationViewState> = combine(
        tables,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { tables, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        RotationViewState(
            tables = tables.firstOr(Tables.Test),
            timerState = timerState,
            finishExerciseState = FinishExerciseState(
                name = exerciseName,
                isFinished = isExerciseFinished,
                pointsCorrect = pointsCorrect,
                pointsIncorrect = pointsIncorrect
            )
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
                    is RotationActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    override suspend fun finishExercise() {
        super.finishExercise()
        updateExercise()
    }

    private suspend fun updateExercise() {
        if (state.value.finishExerciseState.isWin) {
            exercise?.let { updateExerciseInteractor(exercise = it.copy(numberOfWins = it.numberOfWins + 1)) }
        }
    }

    private fun checkChosenValiant(choseVariant: YesNoChoseVariations) {
        viewModelScope.launch {
            val currentTables = state.value.tables

            when (choseVariant) {
                YesNoChoseVariations.YES -> if (currentTables.areTheSame) {
                    pointsCorrect.emit(pointsCorrect.value + 1)
                } else {
                    pointsInCorrect.emit(pointsInCorrect.value + 1)
                }

                YesNoChoseVariations.NO -> if (currentTables.areTheSame) {
                    pointsInCorrect.emit(pointsInCorrect.value + 1)
                } else {
                    pointsCorrect.emit(pointsCorrect.value + 1)
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



