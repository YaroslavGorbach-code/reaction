package yaroslavgorbach.reaction.feature.esercises.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise.Companion.NEED_WINS_TO_OPEN_NEXT_EXERCISE
import yaroslavgorbach.reaction.feature.common.ui.theme.*
import yaroslavgorbach.reaction.utill.singleClickable

@ExperimentalMaterialApi
@Composable
fun ExerciseItem(exercise: Exercise, onExerciseClick: (isAvailable: Boolean) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(EerieBlack)
            .singleClickable { onExerciseClick(exercise.isAvailable) }) {

            ProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                gaps = NEED_WINS_TO_OPEN_NEXT_EXERCISE,
                filledGaps = exercise.numberOfWins
            )

            Icon(
                tint = White,
                painter = painterResource(id = exercise.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
                color = White,
                text = stringResource(id = exercise.name.res),
                style = AppTypography.h3
            )

            Text(
                modifier = Modifier.padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
                color = White,
                text = stringResource(id = exercise.descriptionRes),
                style = AppTypography.subtitle4
            )
        }
    }
}

@Composable
fun ProgressIndicator(modifier: Modifier, gaps: Int, filledGaps: Int) {
    Row(modifier = modifier) {
        repeat(gaps) {
            if (it < filledGaps) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                        .background(color = White, shape = RoundedCornerShape(1.dp))
                        .weight(1f)
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                        .background(color = White30, shape = RoundedCornerShape(1.dp))
                        .weight(1f)
                )
            }

            if (it == gaps.dec()) {
                return@repeat
            } else {
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExerciseItemPreview(exercise: Exercise = Exercise.Test) {
    ReactionTheme {
        Surface {
            ExerciseItem(exercise = exercise) {}
        }
    }
}