package yaroslavgorbach.reaction.feature.exercise.extraNumber.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.Number

@Composable
fun NumberItem(number: Number, onNumberClick: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .clickable { onNumberClick() }
            .size(60.dp)) {

        Box(modifier = Modifier.wrapContentSize()) {
            Text(
                text = number.number.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.caption
            )
        }
    }

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NumberItemPrev(number: Number = Number.Test, onNumberClick: () -> Unit = {}) {

    ReactionTheme {
        NumberItem(number = number, onNumberClick = onNumberClick)
    }
}