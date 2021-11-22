package yaroslavgorbach.reaction.data.exercise.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack

class RepoExerciseImp : RepoExercise {
    override fun observeExtraNumber(): Flow<List<NumberPack>> {
        return flowOf(
            listOf(
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test,
                NumberPack.Test
            )
        )
    }
}