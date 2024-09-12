package yaroslavgorbach.reaction.domain.statistics

import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics
import yaroslavgorbach.reaction.data.statistics.repo.RepoStatistics
import javax.inject.Inject

class SaveStatisticsInteractor @Inject constructor (private val repoStatistics: RepoStatistics) {
    suspend operator fun invoke(statistics: ExerciseStatistics) {
        return repoStatistics.insert(statistics)
    }
}