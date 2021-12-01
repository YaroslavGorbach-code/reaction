package yaroslavgorbach.reaction.data.exercise.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.factory.NumberPacksFactory
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack

@ExperimentalStdlibApi
class RepoExerciseImp : RepoExercise {
    override fun observeExtraNumber(): Flow<List<NumberPack>> {
        return flowOf(NumberPacksFactory().create(100))
    }

    override fun observeExtraWord(): Flow<List<WordPack>> {
        TODO("Not yet implemented")
    }
}