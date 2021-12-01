package yaroslavgorbach.reaction.data.listexercises.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

class RepoExercisesImp : RepoExercises {

    private val exercises: List<Exercise>
        get() = listOf(
            Exercise(
                name = ExerciseName.EXTRA_NUMBER,
                descriptionRes = R.string.description_extra_number,
                instructionRes = R.string.instruction_extra_number,
                levelComplexity = 6,
                benefitsArrayRes = R.array.benefits_extra_number
            )
        )

    override fun observe(): Flow<List<Exercise>> {
        return flowOf(exercises)
    }

}