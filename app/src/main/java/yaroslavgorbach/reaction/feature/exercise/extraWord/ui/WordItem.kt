package yaroslavgorbach.reaction.feature.exercise.extraWord.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.data.exercise.extraWord.model.Word
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.common.ui.theme.White

@Composable
fun WordItem(word: Word, onWordClick: () -> Unit) {
    Box(modifier = Modifier
        .padding(4.dp)
        .background(
            color = MaterialTheme.colors.primary,
            shape = MaterialTheme.shapes.medium
        )
        .clickable { onWordClick() }
        .size(70.dp)

    ) {
        Text(
            fontSize = 14.sp,
            color = White,
            text = word.word,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WordItemPrev(word: Word = Word.Test, onWordClick: () -> Unit = {}) {
    Surface {
        ReactionTheme {
            WordItem(word = word, onWordClick = onWordClick)
        }
    }
}