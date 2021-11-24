package yaroslavgorbach.reaction.feature.exercise.extranumber.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveExtraNumbersInteractor
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.Number
import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise
import yaroslavgorbach.reaction.data.exercise.repo.RepoExerciseImp
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberActions
import yaroslavgorbach.reaction.feature.exercise.extranumber.model.ExtraNumberViewState
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesViewState
import yaroslavgorbach.reaction.utill.TimerCountDown

class ExtraNumberViewModel : ViewModel() {

    private val repo: RepoExercise
        get() = RepoExerciseImp()

    private val observeExtraNumbersInteractor = ObserveExtraNumbersInteractor(repo)

    private val pendingActions = MutableSharedFlow<ExtraNumberActions>()

    private val timerCountDown: TimerCountDown =
        TimerCountDown(
            coroutineScope = viewModelScope,
            millisInFuture = TimerCountDown.ONE_MINUTE,
            countDownInterval = 100
        )

    private val pointsCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    private val pointsInCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    val state: StateFlow<ExtraNumberViewState> = combine(
        observeExtraNumbersInteractor(),
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect
    ) { numberPacks, timerState, pointsCorrect, pointsIncorrect ->
        ExtraNumberViewState(
            numberPacks = numberPacks,
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect
        )

    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ExtraNumberViewState.Empty
    )

    init {
        timerCountDown.start()

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ExtraNumberActions.NumberClick -> onNumberClick(action.number)
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
        }
    }

    fun submitAction(action: ExtraNumberActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

}



