package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R

@Composable
fun ExerciseTopBar(instruction: String) {
    Column {
        Text(
            text = instruction,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
@Preview
fun ExerciseTopBarPreview() {
    Surface {
        MaterialTheme {
            ExerciseTopBar(instruction = stringResource(id = R.string.test_instruction))

        }
    }

}