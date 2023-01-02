package yaroslavgorbach.reaction.domain.exercises

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.repo.RepoExercises
import yaroslavgorbach.reaction.domain.exercises.CheckAndMakeNextExerciseAvailableInteractor
import javax.inject.Inject

class ObserveExercisesInteractor @Inject constructor(
    private val repo: RepoExercises,
    private val checkAndMakeNextExerciseAvailableInteractor: CheckAndMakeNextExerciseAvailableInteractor
) {
    operator fun invoke(): Flow<List<Exercise>> {
        return repo.observe().onEach(checkAndMakeNextExerciseAvailableInteractor::invoke)
    }
}