package yaroslavgorbach.reaction.feature.exercise.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.BuildConfig
import yaroslavgorbach.reaction.domain.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.utill.TimerCountDown
import yaroslavgorbach.reaction.utill.UiMessageManager
import java.util.Date

abstract class BaseExerciseViewModel(
    val exerciseName: ExerciseName, getExerciseInteractor: GetExerciseInteractor
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

    protected open suspend fun finishExercise() {
        isExerciseFinished.emit(true)
        averageTimeForAnswer.emit(answersAverageTime.sum() / answersAverageTime.size)
    }

    protected fun onAnswer() {
        val time = Date().time
        val timeSpendOnAnswer = time - previousAnswerTime
        previousAnswerTime = time
        Log.v("dsasddsa", timeSpendOnAnswer.toString())
        answersAverageTime.add(timeSpendOnAnswer)
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            delay(200)
            uiMessageManager.clearMessage(id)
        }
    }
}