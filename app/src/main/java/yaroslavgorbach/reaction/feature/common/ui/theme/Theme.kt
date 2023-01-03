package yaroslavgorbach.reaction.feature.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = EerieBlack,
    secondary = LightSilver,
    background = White
)

@Composable
fun ReactionTheme(content: @Composable() () -> Unit) {
    val colors = LightColorPalette
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = LightColorPalette.background,)

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        content = content
    )
}