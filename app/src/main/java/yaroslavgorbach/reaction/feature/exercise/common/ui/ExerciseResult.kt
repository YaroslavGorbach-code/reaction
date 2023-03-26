package yaroslavgorbach.reaction.feature.exercise.common.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.CircularProgressIndicatorWithContent
import yaroslavgorbach.reaction.feature.common.ui.buttons.OutlineLargeButton
import yaroslavgorbach.reaction.feature.common.ui.buttons.PrimaryLargeButton
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState
import java.util.concurrent.TimeUnit


@Composable
fun ExerciseResult(
    finishExerciseState: FinishExerciseState,
    onBackClick: () -> Unit,
    onRepeatExercise: () -> Unit
) {

    Box(Modifier.fillMaxSize()) {
        Column {
            Text(
                text = stringResource(id = R.string.exercise_result_title_text),
                style = AppTypography.h1.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                modifier = Modifier.padding(top = 34.dp, start = 20.dp),
            )

            Text(
                text = stringResource(id = finishExerciseState.name.res),
                style = AppTypography.body3,
                modifier = Modifier.padding(start = 20.dp),
            )

            Text(
                text = stringResource(
                    id = R.string.exercise_result_total_rounds_text,
                    finishExerciseState.summaryPoints
                ),
                style = AppTypography.h4,
                modifier = Modifier.padding(top = 40.dp, start = 20.dp),
            )

            val seconds: Long =
                TimeUnit.MILLISECONDS.toSeconds(finishExerciseState.averageTimeForAnswer) % 60
            val milliseconds: Long = finishExerciseState.averageTimeForAnswer % 1000

            Text(
                text = stringResource(
                    id = R.string.exercise_result_avarage_time_to_ansver,
                    seconds,
                    milliseconds
                ),
                style = AppTypography.h4,
                modifier = Modifier.padding(top = 4.dp, start = 20.dp),
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            CircularProgressIndicatorWithContent(
                modifier = Modifier
                    .size(200.dp)
                    .align(CenterHorizontally),
                viewModifier = Modifier.size(200.dp),
                progress = finishExerciseState.correctPresent
            ) {
                Text(
                    text = finishExerciseState.progressString,
                    modifier = Modifier.align(Alignment.Center),
                    style = AppTypography.h2
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
                    .padding(top = 32.dp, end = 20.dp, start = 20.dp),
                text = if (finishExerciseState.isWin) {
                    stringResource(id = R.string.result_good_title)
                } else {
                    stringResource(id = R.string.result_bad_title)
                },
                style = AppTypography.h5,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
                    .padding(top = 8.dp, end = 20.dp, start = 20.dp),
                text = if (finishExerciseState.isWin) {
                    stringResource(id = R.string.result_good, finishExerciseState.progressString)
                } else {
                    stringResource(
                        id = R.string.result_bad,
                        finishExerciseState.winRule.minRounds,
                        finishExerciseState.winRule.minCorrectPresent
                    )
                },
                style = AppTypography.subtitle3,
                textAlign = TextAlign.Center
            )

            if (finishExerciseState.isWin.not()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                        .padding(top = 8.dp, end = 20.dp, start = 20.dp),
                    text = stringResource(id = R.string.result_bad_subtitle),
                    style = AppTypography.body2,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            PrimaryLargeButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.exercise_result_try_again_btn_text)
            ) { onRepeatExercise() }

            OutlineLargeButton(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.exercise_result_finish_btn_text)
            ) { onBackClick() }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ExerciseResultPreview() {
    ReactionTheme {
        ExerciseResult(finishExerciseState = FinishExerciseState(name = ExerciseName.AIRPORT),
            onBackClick = {},
            onRepeatExercise = {})
    }
}