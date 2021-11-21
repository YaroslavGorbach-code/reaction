package yaroslavgorbach.reaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.listexercises.ui.Exercises
import yaroslavgorbach.reaction.feature.listexercises.ui.ExercisesPreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReactionTheme() {

                Exercises(openDetails = {})
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReactionTheme {
        ExercisesPreview()
    }
}