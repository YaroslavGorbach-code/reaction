package yaroslavgorbach.reaction.feature.listexercises.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.data.training.model.Training

@Composable
fun TrainingSurfaceItem(training: Training) {
    Column(Modifier.padding(4.dp)) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            CircularProgressIndicator(
                progress = 1f,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .align(Alignment.Center),
                color = Color.LightGray,
                strokeWidth = 2.5.dp,
            )

            CircularProgressIndicator(
                progress = training.progressFloat,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary,
                strokeWidth = 2.5.dp,
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = training.progressString,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 4.dp),
            text = training.date,
            fontSize = 12.sp,
        )
    }

}

@Composable
@Preview(showBackground = true)
fun TrainingSurfaceItemPreview() {
    MaterialTheme {
        TrainingSurfaceItem(training = Training(emptyList()))
    }
}