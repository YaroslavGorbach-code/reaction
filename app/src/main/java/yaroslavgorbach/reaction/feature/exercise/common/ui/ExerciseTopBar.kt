package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme

@Composable
fun ExerciseTopBar(
    modifier: Modifier = Modifier,
    instruction: String,
    timer: String,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().size(30.dp)) {
            content()
        }

        Text(text = timer, modifier = Modifier
            .align(CenterHorizontally)
            .padding(top = 31.dp),
            style = AppTypography.h1.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        )
        Text(
            text = instruction,
            style = AppTypography.body1,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(showSystemUi = false)
fun ExerciseTopBarPreview() {
    Surface() {
        ReactionTheme {
            ExerciseTopBar(
                instruction = stringResource(id = R.string.description_rotation),
                timer = "00:59",
            )
        }
    }

}