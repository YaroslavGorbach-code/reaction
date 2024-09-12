package yaroslavgorbach.reaction.domain.exercise

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem
import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise
import javax.inject.Inject

class ObserveComplexSortItemsInteractor @Inject constructor(private val repoExercise: RepoExercise) {
    operator fun invoke(): Flow<List<ComplexSortItem>> {
        return repoExercise.observeComplexSort()
    }
}