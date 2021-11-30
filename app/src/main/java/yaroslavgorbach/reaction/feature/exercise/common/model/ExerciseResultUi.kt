package yaroslavgorbach.reaction.feature.exercise.common.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class ExerciseResultUi(
    val exerciseName: ExerciseName,
    val correctPoints: Int,
    val incorrectPoints: Int
) {
    companion object {
        val Test = ExerciseResultUi(ExerciseName.TEST, 10, 5)
    }

    private val isResultGood: Boolean
        get() = correctPresent > 0.95f

    val correctPresent: Float
        get() = ((correctPoints.toFloat() / (incorrectPoints.toFloat() + correctPoints.toFloat())))

    val icon: ImageVector
        get() = if (isResultGood) Icons.Outlined.SentimentSatisfied else Icons.Outlined.SentimentDissatisfied

    val textRes: Int
        get() = if (isResultGood) R.string.result_good else R.string.result_bad

    val progressString: String
        get() = "${(correctPresent * 100).toInt()} %"
}