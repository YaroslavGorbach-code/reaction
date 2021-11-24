package yaroslavgorbach.reaction.feature.exercise.result.model

import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import java.io.Serializable


data class ExerciseResultUi(val exerciseName: ExerciseName, val correctPoints: Int, val incorrectPoints: Int) : Serializable {
    companion object {
        val Test = ExerciseResultUi(ExerciseName.TEST, 10, 5)
    }

    val correctPresent: Float
        get() = ((correctPoints.toFloat() / (incorrectPoints.toFloat() + correctPoints.toFloat()))) / 100f
}