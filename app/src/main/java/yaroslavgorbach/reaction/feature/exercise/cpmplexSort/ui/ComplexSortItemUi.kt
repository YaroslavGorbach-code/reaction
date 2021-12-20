package yaroslavgorbach.reaction.feature.exercise.cpmplexSort.ui

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.common.ui.theme.Shapes
import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.Number

@Composable
fun ComplexSortItemUi(modifier: Modifier = Modifier, item: ComplexSortItem, isClickable: Boolean, onItemClick: () -> Unit) {

    Box(
        modifier = modifier
            .size(100.dp)
            .background(color = item.color, shape = Shapes.medium)
            .clickable(enabled = isClickable, onClick = onItemClick)
    ) {
        Icon(imageVector = item.icon, contentDescription = null, Modifier.fillMaxSize(), tint = Color.White)
    }

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ComplexSortItemUiPreview(
    modifier: Modifier = Modifier,
    item: ComplexSortItem = ComplexSortItem.Test,
    isClickable: Boolean = false,
    onItemClick: () -> Unit = {}
) {

    ReactionTheme {
        ComplexSortItemUi(modifier, item, isClickable, onItemClick)
    }
}