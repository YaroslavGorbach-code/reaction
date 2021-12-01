package yaroslavgorbach.reaction.business.exercise

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise

class ObserveExtraNumbersInteractor(private val repoExercise: RepoExercise) {
    operator fun invoke(): Flow<List<NumberPack>> {
        return repoExercise.observeExtraNumber()
    }
}