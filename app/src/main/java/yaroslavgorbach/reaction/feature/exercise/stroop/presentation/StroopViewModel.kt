package yaroslavgorbach.reaction.feature.exercise.stroop.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercise.ObserveStoopWordsInteractor
import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.feature.exercise.base.BaseExerciseViewModel
import yaroslavgorbach.reaction.data.exercise.stroop.model.WordColorVariant
import yaroslavgorbach.reaction.feature.exercise.stroop.model.StroopActions
import yaroslavgorbach.reaction.feature.exercise.stroop.model.StroopViewState
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class StroopViewModel @Inject constructor(
    observeStoopWordsInteractor: ObserveStoopWordsInteractor
) : BaseExerciseViewModel() {

    private val pendingActions = MutableSharedFlow<StroopActions>()

    private val words: MutableStateFlow<List<StroopWord>> = MutableStateFlow(emptyList())

    val state: StateFlow<StroopViewState> = combine(
        words,
        timerCountDown.state,
        pointsCorrect,
        pointsInCorrect,
        isExerciseFinished
    ) { words, timerState, pointsCorrect, pointsIncorrect, isExerciseFinished ->
        StroopViewState(
            word = words.firstOrNull() ?: StroopWord.Empty,
            timerState = timerState,
            pointsCorrect = pointsCorrect,
            pointsIncorrect = pointsIncorrect,
            isFinished = isExerciseFinished
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
                    is StroopActions.FinishExercise -> finishExercise()
                    else -> error("$action is not handled")
                }
            }
        }
    }


    private fun checkChosenValiant(wordColorVariant: WordColorVariant) {
        viewModelScope.launch {
            val currentWord = state.value.word

            when (wordColorVariant) {
                WordColorVariant.RED -> {
                    when (currentWord.color) {
                        WordColorVariant.RED -> pointsCorrect.emit(pointsCorrect.value + 1)
                        WordColorVariant.GREEN -> pointsInCorrect.emit(pointsInCorrect.value + 1)
                    }
                }
                WordColorVariant.GREEN -> {
                    when (currentWord.color) {
                        WordColorVariant.RED -> pointsInCorrect.emit(pointsInCorrect.value + 1)
                        WordColorVariant.GREEN -> pointsCorrect.emit(pointsCorrect.value + 1)
                    }
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



