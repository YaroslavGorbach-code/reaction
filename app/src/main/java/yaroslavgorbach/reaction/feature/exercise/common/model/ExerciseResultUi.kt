package yaroslavgorbach.reaction.feature.exercise.common.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class ExerciseResultUi(
    val exerciseName: ExerciseName,
    val winRule: WinRule,
    val correctPoints: Int,
    val incorrectPoints: Int
) {
    companion object {
        val Test = ExerciseResultUi(ExerciseName.EXTRA_NUMBER, winRule = WinRule.Default, correctPoints = 30, incorrectPoints = 20)
    }

    val summaryPints: Int
        get() = correctPoints + incorrectPoints

    val correctPresent: Float
        get() = ((correctPoints.toFloat() / (incorrectPoints.toFloat() + correctPoints.toFloat())))

    val icon: ImageVector
        get() = if (isWin) Icons.Outlined.SentimentSatisfied else Icons.Outlined.SentimentDissatisfied

    val isWin: Boolean
        get() = correctPresent > winRule.minCorrectPresent / 100f && summaryPints >= winRule.minRounds

    val progressString: String
        get() = "${(correctPresent * 100).toInt()} %"
}