package yaroslavgorbach.reaction.business.listExercises

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.listExercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listExercises.repo.RepoExercises
import javax.inject.Inject

class ObserveExercisesInteractor @Inject constructor(private val repo: RepoExercises) {
    operator fun invoke(): Flow<List<Exercise>> {
        return repo.observe()
    }
}