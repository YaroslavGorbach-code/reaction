package yaroslavgorbach.reaction.feature.exercise.stroop.presentation

import androidx.lifecycle.viewModelScope
import yaroslavgorbach.reaction.utill.combine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.domain.exercise.ObserveStoopWordsInteractor
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.data.exercise.stroop.model.WordColorVariant
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.feature.exercise.stroop.model.StroopActions
import yaroslavgorbach.reaction.feature.exercise.stroop.model.StroopUiMessage
import yaroslavgorbach.reaction.feature.exercise.stroop.model.StroopViewState
import yaroslavgorbach.reaction.utill.UiMessage
import yaroslavgorbach.reaction.utill.UiMessageManager
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class StroopViewModel @Inject constructor(
    observeStoopWordsInteractor: ObserveStoopWordsInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
    saveStatisticsInteractor: SaveStatisticsInteractor
) : BaseExerciseViewModel(
    exerciseName = ExerciseName.STROOP,
    getExerciseInteractor,
    saveStatisticsInteractor
) {

    private val pendingActions = MutableSharedFlow<StroopActions>()

    private val words: MutableStateFlow<List<StroopWord>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<StroopUiMessage> = UiMessageManager()


    val state: StateFlow<StroopViewState> = combine(
        words,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished,
        uiMessageManager.message,
        averageTimeForAnswer
    ) { words, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished, message, averageTimeForAnswer ->
        StroopViewState(
            word = words.firstOrNull() ?: StroopWord.Empty,
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
        initialValue = StroopViewState.Empty
    )

    init {
        timerCountDown.start()

        viewModelScope.launch {
            observeStoopWordsInteractor()
                .flowOn(Dispatchers.IO)
                .collect(words::emit)

            pendingActions.collect { action ->
                when (action) {
                    is StroopActions.OnChose -> checkChosenValiant(action.chose)
                    is StroopActions.FinishExercise -> finishExercise(state.value.finishExerciseState.isWin)
                    else -> error("$action is not handled")
                }
            }
        }
    }

    override suspend fun finishExercise(isSuccess: Boolean) {
        super.finishExercise(isSuccess)
        updateExercise()
    }

    private suspend fun updateExercise() {
        if (state.value.finishExerciseState.isWin) {
            exercise?.let { updateExerciseInteractor(exercise = it.copy(numberOfWins = it.numberOfWins + 1)) }
        }
    }

    private fun checkChosenValiant(wordColorVariant: WordColorVariant) {
        onAnswer()

        viewModelScope.launch {
            val currentWord = state.value.word

            currentWord.checkAnswer(wordColorVariant) { isCorrect ->
                if (isCorrect) {
                    pointsCorrect.emit(pointsCorrect.value + 1)
                    uiMessageManager.emitMessage(UiMessage(StroopUiMessage.AnswerIsCorrect))
                } else {
                    pointsInCorrect.emit(pointsInCorrect.value + 1)
                    uiMessageManager.emitMessage(UiMessage(StroopUiMessage.AnswerIsNotCorrect))
                }
            }
            words.emit(words.value.drop(1))
        }
    }

    fun submitAction(action: StroopActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}



