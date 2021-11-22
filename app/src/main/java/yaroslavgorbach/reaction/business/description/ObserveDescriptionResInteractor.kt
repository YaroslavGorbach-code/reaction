package yaroslavgorbach.reaction.business.description

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yaroslavgorbach.reaction.business.listexercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

class ObserveDescriptionResInteractor(private val observeExercisesInteractor: ObserveExercisesInteractor) {
    operator fun invoke(exerciseName: ExerciseName): Flow<Int> {
        return observeExercisesInteractor.invoke().map {
            it.first { exercise -> exercise.exerciseName == exerciseName }.descriptionRes
        }
    }
}