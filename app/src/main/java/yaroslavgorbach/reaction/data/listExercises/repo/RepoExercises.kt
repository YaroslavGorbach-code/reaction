package yaroslavgorbach.reaction.data.listExercises.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.listExercises.local.model.Exercise

interface RepoExercises {
    fun observe(): Flow<List<Exercise>>
}