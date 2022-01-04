package yaroslavgorbach.reaction.feature.esercises.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.common.ui.theme.Shapes
import yaroslavgorbach.reaction.data.training.model.Training

@ExperimentalMaterialApi
@Composable
fun TrainingSurface(trainings: List<Training>, onTraining: () -> Unit, modifier: Modifier) {
    Card(
        modifier = modifier,
        onClick = { onTraining() },
        shape = Shapes.small.copy(CornerSize(6.dp)),
        elevation = 4.dp,
    ) {
        Column {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.training),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                style = MaterialTheme.typography.caption,
                modifier = modifier
                    .align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(bottom = 4.dp)
            ) {
                trainings.forEach { training ->
                    item {
                        TrainingSurfaceItem(training = training)
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun TrainingSurfacePreview(
    trainings: List<Training> = listOf(Training.Empty), onTraining: () -> Unit = {}
) {
    ReactionTheme {
        TrainingSurface(trainings = trainings, onTraining = onTraining, Modifier)
    }
}