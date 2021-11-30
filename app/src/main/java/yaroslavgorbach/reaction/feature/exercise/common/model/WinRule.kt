package yaroslavgorbach.reaction.feature.exercise.common.model

interface WinRule {
    val minRounds: Int
    val minCorrectPresent: Int

    companion object Default : WinRule {
        override val minRounds: Int
            get() = 25

        override val minCorrectPresent: Int
            get() = 95
    }
}