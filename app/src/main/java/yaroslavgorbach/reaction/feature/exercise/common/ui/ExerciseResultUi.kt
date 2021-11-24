package yaroslavgorbach.reaction.feature.exercise.common.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

@Parcelize
data class ExerciseResultUi(
    val exerciseName: ExerciseName,
    val correctPoints: Int,
    val incorrectPoints: Int
) : Parcelable {
    companion object {
        val Test = ExerciseResultUi(ExerciseName.TEST, 10, 5)
    }

    val correctPresent: Float
        get() = ((correctPoints.toFloat() / (incorrectPoints.toFloat() + correctPoints.toFloat()))) / 100f
}