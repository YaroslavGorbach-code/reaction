package yaroslavgorbach.reaction.data.exercise.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack

interface RepoExercise {
    fun observeExtraNumber(): Flow<List<NumberPack>>
}