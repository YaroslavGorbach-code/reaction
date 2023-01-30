package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.CircularProgressIndicatorWithContent
import yaroslavgorbach.reaction.feature.common.ui.buttons.OutlineLargeButton
import yaroslavgorbach.reaction.feature.common.ui.buttons.PrimaryLargeButton
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography
import yaroslavgorbach.reaction.feature.common.ui.theme.EerieBlack
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.exercise.common.model.FinishExerciseState

@Composable
fun ExerciseResult(
    finishExerciseState: FinishExerciseState,
    onBackClick: () -> Unit,
    onRepeatExercise: () -> Unit
) {

    Box(Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Result",
                style = AppTypography.h1.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                modifier = Modifier.padding(top = 34.dp, start = 20.dp),
                color = EerieBlack,
            )

            Text(
                text = stringResource(id = finishExerciseState.name.res),
                style = AppTypography.body3,
                modifier = Modifier.padding(start = 20.dp),
                color = EerieBlack
            )

            Text(
                text = "Total rounds: ${finishExerciseState.summaryPoints}",
                style = AppTypography.h4,
                modifier = Modifier.padding(top = 40.dp, start = 20.dp),
                color = EerieBlack
            )

            Text(
                // TODO: add real logic
                text = "Average time on answer: ${30}s",
                style = AppTypography.h4,
                modifier = Modifier.padding(top = 4.dp, start = 20.dp),
                color = EerieBlack
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
                    .padding(top = 32.dp, end = 20.dp, start = 20.dp), text = if (finishExerciseState.isWin) {
                    stringResource(id = R.string.result_good_title)
                } else {
                    stringResource(id = R.string.result_bad_title)
                }, style = AppTypography.h5, textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
                    .padding(top = 8.dp, end = 20.dp, start = 20.dp), text = if (finishExerciseState.isWin) {
                    stringResource(id = R.string.result_good, finishExerciseState.progressString)
                } else {
                    stringResource(
                        id = R.string.result_bad,
                        finishExerciseState.winRule.minRounds,
                        finishExerciseState.winRule.minCorrectPresent
                    )
                }, style = AppTypography.subtitle3, textAlign = TextAlign.Center
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
            PrimaryLargeButton(modifier = Modifier.fillMaxWidth(), text = "Try again") { onRepeatExercise() }

            OutlineLargeButton(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(), text = "Finish"
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