package yaroslavgorbach.reaction.feature.esercises.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics
import yaroslavgorbach.reaction.domain.exercises.MakeExerciseAvailableInteractor
import yaroslavgorbach.reaction.domain.exercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.domain.settings.ChangeIsFirstAppOpenToFalseInteractor
import yaroslavgorbach.reaction.domain.settings.ObserveIsFirstAppOpenInteractor
import yaroslavgorbach.reaction.domain.statistics.ObserveStatisticsInteractor
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesUiMassage
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import yaroslavgorbach.reaction.utill.AdManager
import yaroslavgorbach.reaction.utill.UiMessage
import yaroslavgorbach.reaction.utill.UiMessageManager
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    observeExercisesInteractor: ObserveExercisesInteractor,
    observeIsFirstAppOpenInteractor: ObserveIsFirstAppOpenInteractor,
    changeIsFirstAppOpenToFalseInteractor: ChangeIsFirstAppOpenToFalseInteractor,
    makeExerciseAvailableInteractor: MakeExerciseAvailableInteractor,
    observeStatisticsInteractor: ObserveStatisticsInteractor,
    adManager: AdManager
) : ViewModel() {

    private val pendingActions = MutableSharedFlow<ExercisesActions>()

    private val statistics = MutableStateFlow<List<ExercisesViewState.StatisticState>>(emptyList())

    private val uiMessageManager: UiMessageManager<ExercisesUiMassage> = UiMessageManager()

    private val bottomShitContent: MutableStateFlow<ExercisesViewState.BottomShitContent?>  = MutableStateFlow(null)

    val state: StateFlow<ExercisesViewState> = combine(
        observeExercisesInteractor(),
        observeIsFirstAppOpenInteractor(),
        uiMessageManager.message,
        statistics,
        bottomShitContent
    ) { exercises, isFirstAppOpen, uiMessage, statistic, bottomShitContent ->
        val overAllProgress = try {
            (exercises.filter { it.isNextExerciseAvailable }.size.toFloat() / exercises.size.toFloat())
        } catch (e: Exception) {
            0f
        }

        ExercisesViewState(
            exercises,
            isFirstAppOpen,
            uiMessage,
            statistic,
            overAllProgress,
            bottomShitContent
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = ExercisesViewState.Empty
    )

    init {
        adManager.loadRewordAd()
        adManager.loadInterstitialAd()

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is ExercisesActions.ShowExerciseIsUnavailableBottomShit -> {
                        bottomShitContent.emit(ExercisesViewState.BottomShitContent.UnlockExercise(action.exerciseName))
                        uiMessageManager.emitMessage(
                            UiMessage(
                                ExercisesUiMassage.ShowExerciseIsUnavailable(
                                    action.exerciseName
                                )
                            )
                        )
                    }
                    is ExercisesActions.HideOnboardingDialog -> {
                        changeIsFirstAppOpenToFalseInteractor.invoke()
                    }
                    is ExercisesActions.RequestShowRewordAd -> {
                        uiMessageManager.emitMessage(
                            UiMessage(
                                ExercisesUiMassage.ShowRewardAd(
                                    action.exerciseName
                                )
                            )
                        )
                    }
                    is ExercisesActions.ShowRewordAd -> {
                        adManager.showRewardAd(
                            activity = action.activity,
                        ) {
                            viewModelScope.launch {
                                makeExerciseAvailableInteractor.invoke(exerciseName = action.exerciseName)
                            }
                        }
                    }
                    is ExercisesActions.ShowStaticsBottomShit -> {
                        bottomShitContent.emit(ExercisesViewState.BottomShitContent.Statistics(action.exerciseName))
                        loadStatistics(observeStatisticsInteractor, action)
                        uiMessageManager.emitMessage(
                            UiMessage(
                                ExercisesUiMassage.ShowStatistics(
                                    action.exerciseName
                                )
                            )
                        )
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

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }

    private fun loadStatistics(
        observeStatisticsInteractor: ObserveStatisticsInteractor,
        action: ExercisesActions.ShowStaticsBottomShit
    ) {
        observeStatisticsInteractor(exerciseName = action.exerciseName).map(::mapStatisticsEntityToUiState)
            .onEach(statistics::emit)
            .launchIn(viewModelScope)
    }

    private fun mapStatisticsEntityToUiState(statistics: List<ExerciseStatistics>): List<ExercisesViewState.StatisticState> {

        return statistics.groupBy { it.period }.map { periodWithStatistics ->
            val statisticsForCurrentPeriod = periodWithStatistics.value
            val sumOfAnswers = statisticsForCurrentPeriod.sumOf { it.numberOfAnswers }.toFloat()
            val sumOfCorrectAnswers = statisticsForCurrentPeriod.sumOf { it.correctAnswers }.toFloat()
            val correctPercent = sumOfCorrectAnswers / sumOfAnswers


            val timeToAnswer = statisticsForCurrentPeriod.sumOf { it.averageTimeToAnswer }
                .toFloat() / statisticsForCurrentPeriod.size

            val bars = statisticsForCurrentPeriod.groupBy { it.dayOfWeek }.map { statisticsMap ->
                ExercisesViewState.StatisticState.WinPercentsBar(
                    dayOfWeek = statisticsMap.key,
                    numberOfWins = statisticsMap.value.filter { it.isSuccess }.size.toFloat(),
                    numberOfRounds = statisticsMap.value.size.toFloat()
                )
            }
            ExercisesViewState.StatisticState(
                period = periodWithStatistics.key,
                correctPercent = correctPercent,
                timeToAnswer = timeToAnswer,
                bars = bars
            )
        }
    }
}