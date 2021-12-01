package yaroslavgorbach.reaction.feature.listexercises.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.listExercises.local.model.Exercise
import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName

@Composable
fun ExerciseItem(exercise: Exercise, onExerciseClick: (ExerciseName) -> Unit) {
    Column(modifier = Modifier
        .clickable { onExerciseClick(exercise.name) }
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp)
    )
    {
        Text(
            text = stringResource(id = exercise.name.res),
            style = MaterialTheme.typography.caption,
        )

//        Text(
//            text = stringResource(id = exercise.instructionRes),
//            modifier = Modifier.padding(top = 4.dp),
//            fontSize = 12.sp,
//            style = MaterialTheme.typography.body1,
//        )

        val benefits = stringArrayResource(id = R.array.benefits_extra_number)

        repeat(benefits.size) {
            Row {
                Icon(imageVector = Icons.Outlined.Circle, contentDescription = "", Modifier.size(10.dp).align(CenterVertically))
                Text(text = benefits[it], Modifier.padding(start = 4.dp))
            }
        }

    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExerciseItemPreview(exercise: Exercise = Exercise.Empty) {
    Surface {
        ReactionTheme {
            ExerciseItem(exercise = exercise, {})
        }
    }
}