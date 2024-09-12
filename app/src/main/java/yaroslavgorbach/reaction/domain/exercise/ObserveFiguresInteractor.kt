package yaroslavgorbach.reaction.domain.exercise

import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise
import javax.inject.Inject

class ObserveFiguresInteractor @Inject constructor(val repoExercise: RepoExercise) {
    operator fun invoke() = repoExercise.observeGeoFigures()
}