package yaroslavgorbach.reaction.data.training.model

import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class TrainingExercise(private val exercise: Exercise, val aim: Int, val completed: Int) {

    companion object {
        val Empty = TrainingExercise(
            exercise = Exercise.Empty, aim = 0, completed = 0
        )
    }

    val name: ExerciseName
        get() = exercise.name

    val progressFloat: Float
        get() = (completed.toFloat() / aim.toFloat())

    val progressInt: Int
        get() = ((completed.toFloat() / aim.toFloat()) * 100f).toInt()

    val isFinished: Boolean
        get() = completed >= aim
}
