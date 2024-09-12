package yaroslavgorbach.reaction.domain.statistics

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.data.exercises.local.repo.RepoExercises
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics
import yaroslavgorbach.reaction.data.statistics.repo.RepoStatistics
import java.util.Date
import javax.inject.Inject

class ObserveStatisticsInteractor @Inject constructor (private val repoStatistics: RepoStatistics) {
    operator fun invoke(exerciseName: ExerciseName): Flow<List<ExerciseStatistics>> {
        return repoStatistics.observe(exerciseName)
    }
}