package yaroslavgorbach.reaction.data.statistics.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.data.statistics.dao.StatisticsDao
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics
import java.util.*

class RepoStatisticsImp(private val local: StatisticsDao) : RepoStatistics {
    override fun observe(name: ExerciseName): Flow<List<ExerciseStatistics>> {
        return local.observe(name)
    }

    override suspend fun insert(exercise: ExerciseStatistics) {
        insert(listOf(exercise))
    }

    override suspend fun insert(exercises: List<ExerciseStatistics>) {
        local.insert(exercises)
    }
}