package yaroslavgorbach.reaction.feature.listexercises.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.common.ui.theme.SuperLightGray
import yaroslavgorbach.reaction.data.listExercises.local.model.Exercise
import yaroslavgorbach.reaction.feature.common.ui.CircularProgressIndicatorWithContent
import kotlin.random.Random

@ExperimentalMaterialApi
@Composable
fun ExerciseItem(exercise: Exercise, onExerciseClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(110.dp)
        .background(color = SuperLightGray, shape = MaterialTheme.shapes.medium)
        .clickable { onExerciseClick() }
        .padding(8.dp)

    )
    {
        Text(
            text = stringResource(id = exercise.name.res),
            style = MaterialTheme.typography.caption,
            fontSize = 20.sp,
            modifier = Modifier.weight(0.5f)
        )

        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AddRoad,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .border(width = 0.5.dp, MaterialTheme.colors.primary, shape = CircleShape)
                    .padding(8.dp)
                    .align(Alignment.CenterStart)
            )

            CircularProgressIndicatorWithContent(
                progress = 0.5f,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterEnd)
            ) {
                if (Random.nextBoolean()) {
                    IconWithBeget()
                } else {
                    Icon(
                        Icons.Default.Lock, contentDescription = "",
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxScope.IconWithBeget() {
    Box(
        modifier = Modifier.Companion
            .align(Alignment.Center)
            .size(23.dp)
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = "",
            modifier = Modifier
                .size(23.dp)
                .align(Alignment.Center),
            tint = Color.Gray
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .size(10.dp)
        ) {
            Text(
                text = "39",
                fontSize = 5.sp,
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExerciseItemPreview(exercise: Exercise = Exercise.Test) {
    Surface {
        ReactionTheme {
            ExerciseItem(exercise = exercise) {}
        }
    }
}