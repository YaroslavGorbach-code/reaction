package yaroslavgorbach.reaction.business.listExercises

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.listExercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listExercises.repo.RepoExercises

class ObserveExercisesInteractor(private val repo: RepoExercises) {
    operator fun invoke(): Flow<List<Exercise>> {
        return repo.observe()
    }
}