package yaroslavgorbach.reaction.data.listexercises.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

class RepoExercisesImp : RepoExercises {

    private val testExercises: List<Exercise>
        get() = listOf(
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST),
            Exercise(ExerciseName.TEST)
        )

    override fun observe(): Flow<List<Exercise>> {
        return flowOf(testExercises)
    }

}