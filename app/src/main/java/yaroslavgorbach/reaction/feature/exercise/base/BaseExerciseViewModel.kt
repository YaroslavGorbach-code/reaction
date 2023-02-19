package yaroslavgorbach.reaction.feature.exercise.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.BuildConfig
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.domain.statistics.SaveStatisticsInteractor
import yaroslavgorbach.reaction.utill.TimerCountDown
import yaroslavgorbach.reaction.utill.UiMessageManager
import java.util.*

abstract class BaseExerciseViewModel(
    val exerciseName: ExerciseName,
    getExerciseInteractor: GetExerciseInteractor,
    val saveStatisticsInteractor: SaveStatisticsInteractor
) : ViewModel() {

    abstract val uiMessageManager: UiMessageManager<*>

    protected var exercise: Exercise? = null

    protected val timerCountDown: TimerCountDown = TimerCountDown(
        coroutineScope = viewModelScope,
        millisInFuture = if (BuildConfig.IS_PROD) TimerCountDown.ONE_MINUTE else TimerCountDown.FIFE_SECONDS,
        countDownInterval = 100
    )

    protected val pointsCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    protected val averageTimeForAnswer: MutableStateFlow<Long> = MutableStateFlow(0)

    protected val pointsInCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    protected val isExerciseFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val answersAverageTime: MutableList<Long> = ArrayList()

    private var previousAnswerTime: Long = Date().time

    init {
        viewModelScope.launch {
            exercise = getExerciseInteractor(exerciseName = exerciseName)
        }

        timerCountDown.start()
    }

    protected open suspend fun finishExercise(isSuccess: Boolean) {
        isExerciseFinished.emit(true)
        averageTimeForAnswer.emit(answersAverageTime.sum() / if (answersAverageTime.size != 0) answersAverageTime.size else 1)
        saveStatistics(isSuccess)
    }

    protected fun onAnswer() {
        val time = Date().time
        val timeSpendOnAnswer = time - previousAnswerTime
        previousAnswerTime = time
        answersAverageTime.add(timeSpendOnAnswer)
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            delay(200)
            uiMessageManager.clearMessage(id)
        }
    }

    private suspend fun saveStatistics(isSuccess: Boolean) {
        val statistics = ExerciseStatistics(
            name = exerciseName,
            correctAnswers = pointsCorrect.value,
            averageTimeToAnswer = averageTimeForAnswer.value,
            numberOfAnswers = pointsCorrect.value + pointsInCorrect.value,
            isSuccess = isSuccess,
            date = Date().time
        )

        saveStatisticsInteractor(statistics)
    }
}