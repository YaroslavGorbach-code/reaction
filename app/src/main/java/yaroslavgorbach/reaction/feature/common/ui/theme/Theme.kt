package yaroslavgorbach.reaction.feature.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = ColorPrimaryLight,
    secondary = ColorSecondaryLight,
    background = BgMainLight
)

private val DarkColorPalette = darkColors(
    primary = ColorPrimaryDark,
    secondary = ColorSecondaryDark,
    background = BgMainDark
)

@Composable
fun ReactionTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val colors = if (isDarkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    rememberSystemUiController().setSystemBarsColor(
        color = if (isDarkTheme) DarkColorPalette.background else LightColorPalette.background,
        darkIcons = isDarkTheme.not()
    )
    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        content = content
    )
}