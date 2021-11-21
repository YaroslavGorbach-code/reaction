package yaroslavgorbach.reaction.data.training.model

import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class TrainingExercise(private val exercise: Exercise, val aim: Int, val completed: Int) {

    companion object {
        val Test = TrainingExercise(exercise = Exercise(ExerciseName.TEST), 10, 5)
    }

    val name: ExerciseName
        get() = exercise.exerciseName

    val progress: Float
        get() = (aim.toFloat() / completed.toFloat())

    val isFinished: Boolean
        get() = completed >= aim
}
