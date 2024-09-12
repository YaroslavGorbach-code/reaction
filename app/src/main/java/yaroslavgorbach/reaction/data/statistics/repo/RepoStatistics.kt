package yaroslavgorbach.reaction.data.statistics.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics
import java.util.Date

interface RepoStatistics {
    fun observe(name: ExerciseName): Flow<List<ExerciseStatistics>>

    suspend fun insert(exercise: ExerciseStatistics)

    suspend fun insert(exercises: List<ExerciseStatistics>)
}