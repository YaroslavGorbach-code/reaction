package yaroslavgorbach.reaction.feature.exercise.cpmplexSort.model

import yaroslavgorbach.reaction.feature.exercise.common.model.WinRule

object ComplexSortWinRule : WinRule {
    override val minRounds: Int
        get() = 45

    override val minCorrectPresent: Int
        get() = 95
}