package yaroslavgorbach.reaction

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yaroslavgorbach.reaction.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.description.ui.Description
import yaroslavgorbach.reaction.feature.listexercises.ui.Exercises
import yaroslavgorbach.reaction.feature.listexercises.ui.ExercisesPreview
import yaroslavgorbach.reaction.navigation.NavigationDestination

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ReactionTheme {
                Surface {
                    NavHost(
                        navController,
                        startDestination = NavigationDestination.ExercisesScreen.destination
                    ) {
                        composable(NavigationDestination.ExercisesScreen.destination) {
                            Exercises(
                                openDescription = {
                                    navController.navigate(NavigationDestination.DescriptionScreen.destination)
                                },
                                openTraining = {},
                                openSettings = {}
                            )
                        }

                        composable(NavigationDestination.DescriptionScreen.destination) {
                            Description(openExercise = {})
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReactionTheme {
        ExercisesPreview()
    }
}