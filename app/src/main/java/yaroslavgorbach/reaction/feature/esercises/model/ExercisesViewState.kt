package yaroslavgorbach.reaction.feature.esercises.model

import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.utill.UiMessage

data class ExercisesViewState(
    val exercises: List<Exercise> = emptyList(),
    val isOnboardingDialogVisible: Boolean = false,
    val message: UiMessage<ExercisesUiMassage>? = null,
    val statisticsState: List<StatisticState> = emptyList(),
    val overAllProgress: Float = 0f,
    val bottomShitContent: BottomShitContent? = null
) {
    data class StatisticState(
        val period: String,
        val correctPercent: Float,
        private val timeToAnswer: Float,
        val bars: List<WinPercentsBar>
    ) {
        val averageTimeToAnswerSeconds: Float
            get() = timeToAnswer / 1000f

        data class WinPercentsBar(
            val dayOfWeek: Int,
            val numberOfWins: Float,
            val numberOfRounds: Float
        ) {
            val percentOfWins: Float
                get() = (numberOfWins / numberOfRounds)
        }
    }

    sealed class BottomShitContent {
        class Statistics(val name: ExerciseName): BottomShitContent()
        class UnlockExercise(val name: ExerciseName): BottomShitContent()
    }

    companion object {
        val Empty = ExercisesViewState()
    }
}