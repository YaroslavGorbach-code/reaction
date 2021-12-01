package yaroslavgorbach.reaction.data.exercise.extraWord.model

import yaroslavgorbach.reaction.R
import kotlin.random.Random

data class Word(val wordRes: Int, val isExtra: Boolean) {
    companion object {
        val Test
            get() = Word(R.string.empty, Random.nextBoolean())
    }
}
