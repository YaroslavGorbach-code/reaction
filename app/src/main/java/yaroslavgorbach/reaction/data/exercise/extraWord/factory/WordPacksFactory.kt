package yaroslavgorbach.reaction.data.exercise.extraWord.factory

import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.Number
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import kotlin.random.Random

@ExperimentalStdlibApi
class WordPacksFactory {
    fun create(maxValue: Int = 100): List<NumberPack> {
        return buildList {
            repeat(1000) {
                this.add(
                    NumberPack(buildList {
                        val extraIndex = Random.nextInt(15)
                        val number = Random.nextInt(maxValue)
                        var extraNumber = Random.nextInt(maxValue)

                        while (extraNumber == number) {
                            extraNumber = Random.nextInt(maxValue)
                        }

                        repeat(15) { index ->
                            if (index == extraIndex) {
                                this.add(Number(extraNumber, true))
                            } else {
                                this.add(Number(number, false))
                            }
                        }
                    })
                )
            }
        }
    }
}