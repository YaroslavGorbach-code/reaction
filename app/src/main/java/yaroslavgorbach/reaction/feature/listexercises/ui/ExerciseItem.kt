package yaroslavgorbach.reaction.feature.listexercises.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.listexercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

@Composable
fun ExerciseItem(exercise: Exercise, onExerciseClick: (ExerciseName) -> Unit) {
    Column(modifier = Modifier
        .clickable { onExerciseClick(exercise.name) }
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp))
    {
        Text(
            text = exercise.name.name,
            style = MaterialTheme.typography.caption,
        )

        Row(modifier = Modifier.height(18.dp)) {
            Icon(
                Icons.Outlined.Speed,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
            Text(
                text = stringResource(R.string.level_complexity) + " " + exercise.levelComplexity,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 4.dp),
            )
        }

        Text(
            text = stringResource(id = exercise.instructionRes),
            modifier = Modifier.padding(top = 4.dp),
            fontSize = 12.sp,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExerciseItemPreview(exercise: Exercise = Exercise.Test) {
    Surface {
        ReactionTheme {
            ExerciseItem(exercise = exercise, {})
        }
    }
}