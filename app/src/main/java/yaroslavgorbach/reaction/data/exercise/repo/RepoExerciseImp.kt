package yaroslavgorbach.reaction.data.exercise.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.data.exercise.extranumber.local.factory.NumberPacksFactory
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack

@ExperimentalStdlibApi
class RepoExerciseImp : RepoExercise {
    override fun observeExtraNumber(): Flow<List<NumberPack>> {
        return flowOf(NumberPacksFactory().create(100))
    }
}