package yaroslavgorbach.reaction.feature.esercises.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    observeExercisesInteractor: ObserveExercisesInteractor
) : ViewModel() {
    private val pendingActions = MutableSharedFlow<ExercisesActions>()

    private val isExerciseAvailableDialogShown = MutableStateFlow(false)

    val state: StateFlow<ExercisesViewState> = combine(
        observeExercisesInteractor(),
        isExerciseAvailableDialogShown,
        ::ExercisesViewState
    ).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ExercisesViewState.Empty
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ExercisesActions.HideExerciseIsNotAvailableDialog -> {
                        isExerciseAvailableDialogShown.emit(false)
                    }
                    is ExercisesActions.ShowExerciseIsNotAvailableDialog -> {
                        isExerciseAvailableDialogShown.emit(true)
                    }
                    else -> error("$action is not handled")
                }
            }
        }
    }

    fun submitAction(action: ExercisesActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

}



