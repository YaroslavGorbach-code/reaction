package yaroslavgorbach.reaction.data.listexercises.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise

interface RepoExercises {
    fun observe(): Flow<List<Exercise>>
}