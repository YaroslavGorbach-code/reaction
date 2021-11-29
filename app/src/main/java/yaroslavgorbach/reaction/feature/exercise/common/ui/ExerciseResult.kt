package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
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
import yaroslavgorbach.reaction.feature.common.ui.Toolbar


@Composable
fun ExerciseResult(exerciseResultUi: ExerciseResultUi, onBackClick: () -> Unit, onRepeatExercise: (ExerciseName) -> Unit) {

    Column(Modifier.fillMaxSize()) {
        Toolbar { onBackClick() }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.result),
            style = MaterialTheme.typography.caption,
            fontSize = 45.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(exerciseResultUi.exerciseName.res),
            style = MaterialTheme.typography.body1,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Row(
            Modifier
                .align(CenterHorizontally)
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Box {
                Text(
                    text = exerciseResultUi.progressString,
                    modifier = Modifier.align(Center),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
                CircularProgressIndicator(progress = 1f, color = Color.Red.copy(alpha = 0.4f), modifier = Modifier.size(70.dp))
                CircularProgressIndicator(progress = exerciseResultUi.correctPresent, color = Color.Green, modifier = Modifier.size(70.dp))
            }

            Column(
                Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(id = R.string.correct) + " ${exerciseResultUi.correctPoints}",
                    style = MaterialTheme.typography.caption,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = R.string.incorrect) + " ${exerciseResultUi.incorrectPoints}",
                    style = MaterialTheme.typography.caption,
                    fontSize = 16.sp
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Center)) {

            }

            Row(
                modifier = Modifier
                    .align(BottomCenter)
                    .padding(8.dp),
            ) {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.to_menu),
                        fontSize = 16.sp
                    )
                }

                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.one_more_time),
                        fontSize = 16.sp
                    )
                }
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