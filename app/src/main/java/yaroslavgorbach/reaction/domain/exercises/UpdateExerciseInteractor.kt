package yaroslavgorbach.reaction.domain.exercises

import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.repo.RepoExercises
import javax.inject.Inject

class UpdateExerciseInteractor @Inject constructor(private val repoExercises: RepoExercises) {

    suspend operator fun invoke(exercise: Exercise) = repoExercises.update(exercise)
}