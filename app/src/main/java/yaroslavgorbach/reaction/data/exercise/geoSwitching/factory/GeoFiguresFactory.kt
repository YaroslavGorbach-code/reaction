package yaroslavgorbach.reaction.data.exercise.geoSwitching.factory

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.ui.graphics.Color
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercise.extraWord.model.Word
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.data.exercise.geoSwitching.model.FigureVariant
import yaroslavgorbach.reaction.data.exercise.geoSwitching.model.GeoFigure
import yaroslavgorbach.reaction.data.exercise.geoSwitching.model.TaskVariant
import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord
import yaroslavgorbach.reaction.data.exercise.stroop.model.WordColorVariant
import kotlin.random.Random

@ExperimentalStdlibApi
class GeoFiguresFactory() {

    private val colors = listOf(Color.Green, Color.Red, Color.Blue)
    private val figures = FigureVariant.values().toList()
    private val tasks = TaskVariant.values().toList()

    fun create(): List<GeoFigure> {
        return buildList {
            repeat(1000) {
                add(GeoFigure(figure = figures.random(), color = colors.random(), tasks.random()))
            }
        }
    }
}