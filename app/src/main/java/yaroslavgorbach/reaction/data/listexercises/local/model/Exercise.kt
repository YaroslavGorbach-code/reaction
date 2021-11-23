package yaroslavgorbach.reaction.data.listexercises.local.model

import yaroslavgorbach.reaction.R
import kotlin.random.Random

data class Exercise(val name: ExerciseName, val descriptionRes: Int, val instructionRes: Int, val levelComplexity: Int) {

    companion object {
        val Test = Exercise(ExerciseName.TEST, R.string.test_description, R.string.test_instruction, Random.nextInt(10))
    }
}
