package yaroslavgorbach.reaction.feature.exercise.faceControl.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face

@Composable
fun FaceItem(face: Face, onFaceClick: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .clickable { onFaceClick() }
            .size(60.dp)) {
        Image(
            imageVector = face.icon,
            contentDescription = ""
        )
    }

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FaceItemPrev(face: Face = Face.Test, onFaceClick: () -> Unit = {}) {

    ReactionTheme {
        FaceItem(face = face, onFaceClick = onFaceClick)
    }
}