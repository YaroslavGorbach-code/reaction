package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.common.ui.Toolbar

@Composable
fun ExerciseTopBar(
    modifier: Modifier = Modifier,
    instruction: String,
    timeProgress: Float,
    time: String,
    onBack: () -> Unit
) {
    Column(modifier = modifier) {
        Toolbar { onBack() }

        LinearProgressIndicator(
            progress = timeProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        Text(
            textAlign = TextAlign.Center,
            text = time,
            fontSize = 30.sp,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            text = instruction,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun ExerciseTopBarPreview() {
    Surface {
        ReactionTheme {
            ExerciseTopBar(
                instruction = stringResource(id = R.string.test_instruction),
                timeProgress = 0.5f,
                time = "00:59",
                onBack = {}
            )
        }
    }
}