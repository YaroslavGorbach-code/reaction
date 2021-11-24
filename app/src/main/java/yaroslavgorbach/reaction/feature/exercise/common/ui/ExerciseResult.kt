package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName


@Composable
fun ExerciseResult(exerciseResultUi: ExerciseResultUi, onBackClick: () -> Unit, onRepeatExercise: (ExerciseName) -> Unit) {

    Column(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.result),
            style = MaterialTheme.typography.caption,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(exerciseResultUi.exerciseName.res),
            style = MaterialTheme.typography.body1,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Row(
            Modifier
                .align(CenterHorizontally)
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Box {
                Text(text = "10%", modifier = Modifier.align(Center), textAlign = TextAlign.Center, fontSize = 16.sp)
                CircularProgressIndicator(progress = 1f, color = Color.Red.copy(alpha = 0.4f), modifier = Modifier.size(50.dp))
                CircularProgressIndicator(progress = 0.5f, color = Color.Green, modifier = Modifier.size(50.dp))
            }

            Column(Modifier.padding(start = 16.dp).align(Alignment.CenterVertically)) {
                Text(text = stringResource(id = R.string.correct) + " ${exerciseResultUi.correctPoints}")
                Text(text = stringResource(id = R.string.incorrect) + " ${ exerciseResultUi.incorrectPoints}")
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun ExerciseResultPreview() {

    ReactionTheme {
        ExerciseResult(exerciseResultUi = ExerciseResultUi.Test, onBackClick = {}, onRepeatExercise = {})
    }
}