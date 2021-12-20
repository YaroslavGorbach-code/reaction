package yaroslavgorbach.reaction.data.listExercises.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listExercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName

class RepoExercisesImp : RepoExercises {

    private val exercises: List<Exercise>
        get() = listOf(
            Exercise(
                name = ExerciseName.EXTRA_NUMBER,
                descriptionRes = R.string.description_extra_number,
                instructionRes = R.string.instruction_extra_number,
                levelComplexity = 6,
                benefitsArrayRes = R.array.benefits_extra_number
            ),
            Exercise(
                name = ExerciseName.EXTRA_WORD,
                descriptionRes = R.string.description_extra_word,
                instructionRes = R.string.instruction_extra_word,
                levelComplexity = 6,
                benefitsArrayRes = R.array.benefits_extra_word
            ),
            Exercise(
                name = ExerciseName.FACE_CONTROL,
                descriptionRes = R.string.description_face_control,
                instructionRes = R.string.instruction_face_control,
                levelComplexity = 6,
                benefitsArrayRes = R.array.benefits_face_control
            ),
            Exercise(
                name = ExerciseName.COMPLEX_SORT,
                descriptionRes = R.string.description_complex_sort,
                instructionRes = R.string.empty,
                levelComplexity = 6,
                benefitsArrayRes = R.array.benefits_face_control
            )
        )

    override fun observe(): Flow<List<Exercise>> {
        return flowOf(exercises)
    }

}