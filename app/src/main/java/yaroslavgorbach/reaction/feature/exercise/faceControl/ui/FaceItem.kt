package yaroslavgorbach.reaction.feature.exercise.faceControl.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme

@Composable
fun FaceItem(face: Face, onFaceClick: () -> Unit) {
    Image(
        imageVector = face.icon,
        contentDescription = "",
        colorFilter = ColorFilter.tint(Color.White),
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onFaceClick() }
            .size(60.dp)
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FaceItemPrev(face: Face = Face.Test, onFaceClick: () -> Unit = {}) {

    ReactionTheme {
        FaceItem(face = face, onFaceClick = onFaceClick)
    }
}