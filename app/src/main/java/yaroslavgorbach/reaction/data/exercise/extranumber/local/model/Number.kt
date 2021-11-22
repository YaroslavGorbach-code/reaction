package yaroslavgorbach.reaction.data.exercise.extranumber.local.model

import kotlin.random.Random

data class Number(val number: Int, val isExtra: Boolean) {
    companion object {
        val Test = Number(Random.nextInt(100), Random.nextBoolean())
    }
}
