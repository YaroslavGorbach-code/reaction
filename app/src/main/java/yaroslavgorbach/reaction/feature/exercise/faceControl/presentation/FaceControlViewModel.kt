package yaroslavgorbach.reaction.feature.exercise.faceControl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveFaceControlInteractor
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlActions
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlViewState
import javax.inject.Inject

@HiltViewModel
class FaceControlViewModel @Inject constructor(
    observeFaceControlInteractor: ObserveFaceControlInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<FaceControlActions>()

    private val facePacks: MutableStateFlow<List<FacePack>> = MutableStateFlow(emptyList())

    var state: StateFlow<FaceControlViewState> = combine(
        facePacks,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { facePacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        FaceControlViewState(
            facePacks = facePacks,
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = FaceControlViewState.Empty
    )

    init {
        viewModelScope.launch {
            observeFaceControlInteractor()
                .flowOn(Dispatchers.IO)
                .collect(facePacks::emit)

            pendingActions.collect { action ->
                when (action) {
                    is FaceControlActions.FaceClick -> onFaceClick(action.face)
                    is FaceControlActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onFaceClick(face: Face) {
        viewModelScope.launch {
            if (face.isDissatisfied) {
                pointsCorrect.emit(pointsCorrect.value + 1)
            } else {
                pointsInCorrect.emit(pointsInCorrect.value + 1)
            }

            facePacks.emit(facePacks.value.drop(1))
        }
    }

    fun submitAction(action: FaceControlActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}



