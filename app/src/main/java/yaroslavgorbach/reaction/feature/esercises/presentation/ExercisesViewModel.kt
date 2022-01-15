package yaroslavgorbach.reaction.feature.esercises.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.business.settings.ChangeIsFirstAppOpenToFalseInteractor
import yaroslavgorbach.reaction.business.settings.ObserveIsFirstAppOpenInteractor
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    observeExercisesInteractor: ObserveExercisesInteractor,
    observeIsFirstAppOpenInteractor: ObserveIsFirstAppOpenInteractor,
    changeIsFirstAppOpenToFalseInteractor: ChangeIsFirstAppOpenToFalseInteractor
) : ViewModel() {
    private val pendingActions = MutableSharedFlow<ExercisesActions>()

    private val isExerciseAvailableDialogShown = MutableStateFlow(false)


    val state: StateFlow<ExercisesViewState> = combine(
        observeExercisesInteractor(),
        isExerciseAvailableDialogShown,
        observeIsFirstAppOpenInteractor(),
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
                    is ExercisesActions.HideOnboardingDialog -> {
                        changeIsFirstAppOpenToFalseInteractor.invoke()
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



