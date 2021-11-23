package yaroslavgorbach.reaction.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.description.ui.Description
import yaroslavgorbach.reaction.feature.listexercises.ui.Exercises

sealed class Screen(val route: String) {
    object Exercises : Screen("Exercises")
}

@ExperimentalMaterialApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Exercises.route,
        modifier = modifier,
    ) {
        addExercisesTopLevel(navController)
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addExercisesTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Exercises.route,
        startDestination = LeafScreen.Exercises.createRoute(Screen.Exercises),
    ) {
        addExercises(navController, Screen.Exercises)
        addDescription(navController, Screen.Exercises)
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addExercises(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Exercises.createRoute(root)) {
        Exercises(openDescription = { exerciseName ->
            navController.navigate(LeafScreen.ShowDescription.createRoute(root = root, exerciseName = exerciseName))
        }, openTraining = { }, openSettings = {})
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addDescription(
    navController: NavController,
    root: Screen,
) {
    composable(
        LeafScreen.ShowDescription.createRoute(root), arguments = listOf(
            navArgument("exerciseName") {
                type = NavType.EnumType(ExerciseName::class.java)
            })
    ) {
        Description(openExercise = {
            //navController.navigate(LeafScreen.ShowExercise.createRoute(root = root, exerciseName = it))
        })
    }
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Exercises : LeafScreen("Exercises")

    object ShowDescription : LeafScreen("Description/{exerciseName}") {
        fun createRoute(root: Screen, exerciseName: ExerciseName): String {
            return "${root.route}/Description/$exerciseName"
        }
    }

    object ShowExercise : LeafScreen("Exercise/{exerciseName}") {
        fun createRoute(root: Screen, exerciseName: ExerciseName): String {
            return "${root.route}/Exercise/$exerciseName"
        }
    }
}

