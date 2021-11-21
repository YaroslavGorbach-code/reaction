package yaroslavgorbach.reaction.feature.listexercises.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.listexercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.data.listexercises.repo.RepoExercises
import yaroslavgorbach.reaction.data.listexercises.repo.RepoExercisesImp
import yaroslavgorbach.reaction.feature.listexercises.model.ExerciseUi
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.listexercises.model.ExercisesViewState

class ExercisesViewModel : ViewModel() {

    private val repo: RepoExercises
        get() = RepoExercisesImp()

    private val observeExercisesInteractor: ObserveExercisesInteractor
        get() = ObserveExercisesInteractor(repo)

    private val pendingActions = MutableSharedFlow<ExercisesActions>()

    val state: StateFlow<ExercisesViewState> = combine(
        observeExercisesInteractor().map { it.map(::ExerciseUi) }
    ) { exercises ->
        ExercisesViewState(exercises = exercises[0])
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ExercisesViewState.Empty
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ExercisesActions.OpenDetails -> error("navigation actions is not supported in view models")
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



