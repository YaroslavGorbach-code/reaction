package yaroslavgorbach.reaction.feature.exercise.faceControl.presentation

import android.app.Activity
import androidx.lifecycle.viewModelScope
import yaroslavgorbach.reaction.utill.combine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.domain.exercise.ObserveFaceControlInteractor
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlActions
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlUiMessage
import yaroslavgorbach.reaction.feature.exercise.faceControl.model.FaceControlViewState
import yaroslavgorbach.reaction.utill.AdManager
import yaroslavgorbach.reaction.utill.UiMessage
import yaroslavgorbach.reaction.utill.UiMessageManager
import javax.inject.Inject

@HiltViewModel
class FaceControlViewModel @Inject constructor(
    observeFaceControlInteractor: ObserveFaceControlInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
    saveStatisticsInteractor: SaveStatisticsInteractor,
    addManager: AdManager
) : BaseExerciseViewModel(
    exerciseName = ExerciseName.FACE_CONTROL,
    getExerciseInteractor,
    saveStatisticsInteractor,
    addManager
) {

    private val pendingActions = MutableSharedFlow<FaceControlActions>()

    private val facePacks: MutableStateFlow<List<FacePack>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<FaceControlUiMessage> = UiMessageManager()

    var state: StateFlow<FaceControlViewState> = combine(
        facePacks,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished,
        uiMessageManager.message,
        averageTimeForAnswer
    ) { facePacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished, message, averageTimeForAnswer ->
        FaceControlViewState(
            facePacks = facePacks,
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
        initialValue = FaceControlViewState.Empty
    )

    init {
        viewModelScope.launch {
            observeFaceControlInteractor().flowOn(Dispatchers.IO).collect(facePacks::emit)

            pendingActions.collect { action ->
                when (action) {
                    is FaceControlActions.FaceClick -> onFaceClick(action.face)
                    is FaceControlActions.FinishExercise -> finishExercise(state.value.finishExerciseState.isWin, action.activity)
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

    private fun onFaceClick(face: Face) {
        onAnswer()

        viewModelScope.launch {
            if (face.isDissatisfied) {
                pointsCorrect.emit(pointsCorrect.value + 1)
                uiMessageManager.emitMessage(UiMessage(FaceControlUiMessage.AnswerIsCorrect))
            } else {
                pointsInCorrect.emit(pointsInCorrect.value + 1)
                uiMessageManager.emitMessage(UiMessage(FaceControlUiMessage.AnswerIsNotCorrect))
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



