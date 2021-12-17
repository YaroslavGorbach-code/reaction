package yaroslavgorbach.reaction.feature.exercise.extraWord.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveExtraWordsInteractor
import yaroslavgorbach.reaction.data.exercise.extraWord.model.Word
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordActions
import yaroslavgorbach.reaction.feature.exercise.extraWord.model.ExtraWordViewState
import javax.inject.Inject

@HiltViewModel
class ExtraWordViewModel @Inject constructor(
    observeExtraWordsInteractor: ObserveExtraWordsInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<ExtraWordActions>()

    private val wordPacks: MutableStateFlow<List<WordPack>> = MutableStateFlow(emptyList())

    val state: StateFlow<ExtraWordViewState> = combine(
        wordPacks,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { wordPacks, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        ExtraWordViewState(
            wordPacks = wordPacks,
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
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
                    is ExtraWordActions.FinishExercise -> onFinishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }

    private fun onWordClick(word: Word) {
        viewModelScope.launch {
            if (word.isExtra) {
                pointsCorrect.emit(pointsCorrect.value + 1)
            } else {
                pointsInCorrect.emit(pointsInCorrect.value + 1)
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



