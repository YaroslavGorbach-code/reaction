package yaroslavgorbach.reaction.feature.exercise.extraWord.presentation

import android.app.Activity
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.domain.exercise.ObserveExtraWordsInteractor
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.exercises.UpdateExerciseInteractor
import yaroslavgorbach.reaction.data.exercise.extraWord.model.Word
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordActions
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordUiMessage
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordViewState
import yaroslavgorbach.reaction.utill.AdManager
import yaroslavgorbach.reaction.utill.UiMessage
import yaroslavgorbach.reaction.utill.UiMessageManager
import yaroslavgorbach.reaction.utill.combine
import javax.inject.Inject

@HiltViewModel
class ExtraWordViewModel @Inject constructor(
    observeExtraWordsInteractor: ObserveExtraWordsInteractor,
    getExerciseInteractor: GetExerciseInteractor,
    private val updateExerciseInteractor: UpdateExerciseInteractor,
    saveStatisticsInteractor: SaveStatisticsInteractor,
    addManager: AdManager
) : BaseExerciseViewModel(
    exerciseName = ExerciseName.EXTRA_WORD,
    getExerciseInteractor = getExerciseInteractor,
    saveStatisticsInteractor,
    addManager
) {

    private val pendingActions = MutableSharedFlow<ExtraWordActions>()

    private val wordPacks: MutableStateFlow<List<WordPack>> = MutableStateFlow(emptyList())

    override val uiMessageManager: UiMessageManager<ExtraWordUiMessage> = UiMessageManager()

    val state: StateFlow<ExtraWordViewState> = combine(
        wordPacks,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished,
        uiMessageManager.message,
        averageTimeForAnswer
    ) { wordPacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished, message, averageTimeForAnswer ->
        ExtraWordViewState(
            wordPacks = wordPacks,
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
        initialValue = ExtraWordViewState.Empty
    )

    init {
        viewModelScope.launch {
            observeExtraWordsInteractor()
                .flowOn(Dispatchers.IO)
                .collect(wordPacks::emit)

            pendingActions.collect { action ->
                when (action) {
                    is ExtraWordActions.WordClick -> onWordClick(action.word)
                    is ExtraWordActions.FinishExercise -> finishExercise(
                        state.value.finishExerciseState.isWin,
                        action.activity
                    )
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

    private fun onWordClick(word: Word) {
        onAnswer()

        viewModelScope.launch {
            if (word.isExtra) {
                pointsCorrect.emit(pointsCorrect.value + 1)
                uiMessageManager.emitMessage(UiMessage(ExtraWordUiMessage.AnswerIsCorrect))
            } else {
                pointsInCorrect.emit(pointsInCorrect.value + 1)
                uiMessageManager.emitMessage(UiMessage(ExtraWordUiMessage.AnswerIsNotCorrect))
            }

            wordPacks.emit(wordPacks.value.drop(1))
        }
    }

    fun submitAction(action: ExtraWordActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}



