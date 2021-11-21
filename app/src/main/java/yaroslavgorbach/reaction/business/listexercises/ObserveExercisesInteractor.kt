package yaroslavgorbach.reaction.business.listexercises

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.repo.RepoExercises

class ObserveExercisesInteractor(private val repo: RepoExercises) {
    operator fun invoke(): Flow<List<Exercise>> {
        return repo.observe()
    }
}