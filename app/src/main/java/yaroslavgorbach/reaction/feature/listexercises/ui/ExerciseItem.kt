package yaroslavgorbach.reaction.feature.listexercises.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.listexercises.model.ExerciseUi

@Composable
fun ExerciseItem(exercise: ExerciseUi, onExerciseClick: (ExerciseName) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onExerciseClick(exercise.name) }
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = exercise.name.name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            style = MaterialTheme.typography.caption
        )

        Row(modifier = Modifier.height(18.dp)) {
            Icon(Icons.Outlined.Speed, contentDescription = null)
            Text(
                text = stringResource(R.string.level_complexity) + " " + exercise.levelComplexity,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Text(text = exercise.shortDescription, modifier = Modifier.padding(top = 4.dp), style = MaterialTheme.typography.subtitle1, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseItemPreview(exercise: ExerciseUi = ExerciseUi.Empty) {
    ReactionTheme {
        ExerciseItem(exercise = exercise, {})
    }
}