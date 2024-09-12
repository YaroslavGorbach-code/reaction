package yaroslavgorbach.reaction.domain.exercise

import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise
import javax.inject.Inject

class ObserveTablesInteractor @Inject constructor(private val repoExercise: RepoExercise) {
    operator fun invoke() = repoExercise.observeTables()
}