package yaroslavgorbach.reaction.feature.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressIndicatorWithContent(
    modifier: Modifier,
    viewModifier: Modifier,
    progress: Float,
    strokeColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = 16.dp,
    isVisible: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        if (isVisible) {
            CircularProgressIndicator(
                progress = progress,
                color = strokeColor,
                strokeWidth = strokeWidth,
                modifier = viewModifier
                    .drawBehind {
                    drawCircle(
                        color = backgroundColor,
                        radius = (size.maxDimension / 2.0f) - (strokeWidth.value),
                        style = Fill
                    )
                }
            )
        }
        content()
    }
}