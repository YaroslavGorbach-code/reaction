package yaroslavgorbach.reaction.feature.exercise.common.ui

import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class ExerciseResultUi(
    val exerciseName: ExerciseName,
    val correctPoints: Int,
    val incorrectPoints: Int,
    val isGood: Boolean
) {
    companion object {
        val Test = ExerciseResultUi(ExerciseName.TEST, 10, 5, false)
    }

    val correctPresent: Float
        get() = ((correctPoints.toFloat() / (incorrectPoints.toFloat() + correctPoints.toFloat())))

    val progressString: String
        get() = "${(correctPresent * 100).toInt()} %"
}