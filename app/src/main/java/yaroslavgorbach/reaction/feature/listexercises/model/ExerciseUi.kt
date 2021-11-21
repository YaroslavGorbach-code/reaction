package yaroslavgorbach.reaction.feature.listexercises.model

import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class ExerciseUi(private val exercise: Exercise) {

    companion object {
        val Empty = ExerciseUi(Exercise(ExerciseName.TEST))
    }

    val name: ExerciseName
        get() = exercise.exerciseName

    val shortDescription: String
        get() = "You need just test it and that is all ypu need because your love is a lie"

    val levelComplexity: Int
        get() = 10
}