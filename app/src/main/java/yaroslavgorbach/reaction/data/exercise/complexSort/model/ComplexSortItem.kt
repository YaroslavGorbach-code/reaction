package yaroslavgorbach.reaction.data.exercise.complexSort.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data class ComplexSortItem(val icon: ImageVector, val color: Color, val isSimilar: Boolean) {
    companion object {
        val Test = ComplexSortItem(Icons.Outlined.AccessTime, Color.Green, Random.nextBoolean())

        val ExampleFirst = ComplexSortItem(Icons.Outlined.AccessibilityNew, Color.Red, false)
        val ExampleSecond = ComplexSortItem(Icons.Outlined.SelfImprovement, Color.Green, false)
        val ExampleThird = ComplexSortItem(Icons.Outlined.Hiking, Color.Blue, false)
    }
}
