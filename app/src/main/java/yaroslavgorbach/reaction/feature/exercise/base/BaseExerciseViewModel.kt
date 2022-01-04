package yaroslavgorbach.reaction.feature.exercise.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.business.exercises.GetExerciseInteractor
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.utill.TimerCountDown

abstract class BaseExerciseViewModel(
    val exerciseName: ExerciseName,
    getExerciseInteractor: GetExerciseInteractor
) : ViewModel() {

    protected var exercise: Exercise? = null

    protected val timerCountDown: TimerCountDown =
        TimerCountDown(
            coroutineScope = viewModelScope,
            millisInFuture = TimerCountDown.ONE_MINUTE,
            countDownInterval = 100
        )

    protected val pointsCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    protected val pointsInCorrect: MutableStateFlow<Int> = MutableStateFlow(0)

    protected val isExerciseFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            exercise = getExerciseInteractor(exerciseName = exerciseName)
        }

        timerCountDown.start()
    }

    protected open suspend fun finishExercise() = isExerciseFinished.emit(true)

}