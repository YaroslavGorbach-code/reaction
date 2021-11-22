package yaroslavgorbach.reaction.feature.description.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.description.ObserveDescriptionResInteractor
import yaroslavgorbach.reaction.business.listexercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.data.listexercises.repo.RepoExercises
import yaroslavgorbach.reaction.data.listexercises.repo.RepoExercisesImp
import yaroslavgorbach.reaction.feature.description.model.DescriptionActions
import yaroslavgorbach.reaction.feature.description.model.DescriptionViewState

class DescriptionViewModel : ViewModel() {
    private val exerciseName: ExerciseName
        get() = ExerciseName.TEST

    private val repo: RepoExercises
        get() = RepoExercisesImp()

    private val observeExercisesInteractor: ObserveExercisesInteractor
        get() = ObserveExercisesInteractor(repo)

    private val observeDescriptionInteractor: ObserveDescriptionResInteractor
        get() = ObserveDescriptionResInteractor(observeExercisesInteractor)

    private val pendingActions = MutableSharedFlow<DescriptionActions>()

    val state: StateFlow<DescriptionViewState> = combine(
        observeDescriptionInteractor(exerciseName)
    ) { description ->
        DescriptionViewState(descriptionRes = description[0])
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DescriptionViewState.Empty
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    else -> error("action is not handled")
                }
            }
        }
    }

    fun submitAction(action: DescriptionActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

}