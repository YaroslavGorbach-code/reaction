package yaroslavgorbach.reaction.feature.exercise.faceControl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveExtraWordsInteractor
import yaroslavgorbach.reaction.business.exercise.ObserveFaceControlInteractor
import yaroslavgorbach.reaction.data.exercise.extraWord.model.Word
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordActions
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordViewState
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlActions
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlViewState
import yaroslavgorbach.reaction.utill.TimerCountDown
import javax.inject.Inject

@HiltViewModel
class FaceControlViewModel @Inject constructor(
    observeFaceControlInteractor: ObserveFaceControlInteractor
) : ViewModel() {

    private val pendingActions = MutableSharedFlow<FaceControlActions>()

    private val timerCountDown: TimerCountDown =
        TimerCountDown(
            coroutineScope = viewModelScope,
            millisInFuture = TimerCountDown.ONE_MINUTE,
            countDownInterval = 100
        )

    private val pointsCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    private val pointsInCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    private val isExerciseFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

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
        timerCountDown.start()

        viewModelScope.launch {
            observeFaceControlInteractor()
                .flowOn(Dispatchers.IO)
                .collect(facePacks::emit)

            pendingActions.collect { action ->
                when (action) {
                    is FaceControlActions.FaceClick -> onFaceClick(action.face)
                    is FaceControlActions.FinishExercise -> onFinishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onFinishExercise() {
        viewModelScope.launch {
            isExerciseFinished.emit(true)
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



