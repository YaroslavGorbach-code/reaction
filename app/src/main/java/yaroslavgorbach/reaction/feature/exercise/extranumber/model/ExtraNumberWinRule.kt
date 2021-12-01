package yaroslavgorbach.reaction.feature.exercise.extranumber.model

import yaroslavgorbach.reaction.feature.exercise.common.model.WinRule

object ExtraNumberWinRule : WinRule {
    override val minRounds: Int
        get() = 45

    override val minCorrectPresent: Int
        get() = 90
}