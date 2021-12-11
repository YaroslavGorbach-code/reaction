package yaroslavgorbach.reaction.data.exercise.faceControl.factory

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentSatisfied
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercise.extraWord.model.Word
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import kotlin.random.Random

@ExperimentalStdlibApi
class FacePacksFactory() {

    fun create(): List<FacePack> {
        return buildList {
            repeat(1000) {
                this.add(
                    FacePack(buildList {
                        val extraIndex: Int = Random.nextInt(15)

                        repeat(15) { index ->
                            if (index == extraIndex) {
                                this.add(Face(Icons.Outlined.SentimentDissatisfied, true))
                            } else {
                                this.add(Face(Icons.Outlined.SentimentSatisfied, false))
                            }
                        }
                    })
                )
            }
        }
    }
}