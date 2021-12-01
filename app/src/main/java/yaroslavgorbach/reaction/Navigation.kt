package yaroslavgorbach.reaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.description.ui.Description
import yaroslavgorbach.reaction.feature.exercise.extraNumber.ui.ExtraNumbers
import yaroslavgorbach.reaction.feature.listexercises.ui.Exercises

const val EXERCISE_NAME_ARG = "EXERCISE_NAME_ARG"

sealed class Screen(val route: String) {
    object Exercises : Screen("Exercises")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Exercises : LeafScreen("Exercises")
    object ExtraNumbers : LeafScreen("ExtraNumbers")
    object ExtraWords : LeafScreen("ExtraWords")

    object ShowDescription : LeafScreen("Description/{${EXERCISE_NAME_ARG}}") {
        fun createRoute(root: Screen, exerciseName: ExerciseName): String {
            return "${root.route}/Description/$exerciseName"
        }
    }
}


@ExperimentalFoundationApi
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

@ExperimentalFoundationApi
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
        addExtraNumbersExercise(navController, Screen.Exercises)
        addExtraWordsExercise(navController, Screen.Exercises)
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addExercises(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Exercises.createRoute(root)) {
        Exercises(openDescription = { exerciseName ->
            navController.navigate(
                LeafScreen.ShowDescription.createRoute(
                    root = root,
                    exerciseName = exerciseName
                )
            )
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
            navArgument(EXERCISE_NAME_ARG) {
                type = NavType.EnumType(ExerciseName::class.java)
            })
    ) { backStackEntry ->
        Description(
            exerciseName = backStackEntry.arguments?.getSerializable(EXERCISE_NAME_ARG) as ExerciseName,
            openExercise = { exerciseName ->
                navController.navigate(
                    mapExerciseNameToLeafScreen(exerciseName = exerciseName).createRoute(root = root)
                ) {
                    popUpTo(LeafScreen.ShowDescription.createRoute(root)) {
                        inclusive = true
                    }
                }
            }, onBackClick = { navController.popBackStack() })
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addExtraNumbersExercise(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.ExtraNumbers.createRoute(root)) {
        ExtraNumbers(onBackClick = { navController.popBackStack() }, onRepeatExerciseClick = {
            navController.navigate(
                LeafScreen.ShowDescription.createRoute(
                    root = root,
                    exerciseName = ExerciseName.EXTRA_NUMBER
                )
            ) {
                popUpTo(LeafScreen.Exercises.createRoute(root = root)) {
                    inclusive = false
                }
            }
        })
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addExtraWordsExercise(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.ExtraWords.createRoute(root)) {
        ExtraWords(onBackClick = { navController.popBackStack() }, onRepeatExerciseClick = {
            navController.navigate(
                LeafScreen.ShowDescription.createRoute(
                    root = root,
                    exerciseName = ExerciseName.EXTRA_WORD
                )
            ) {
                popUpTo(LeafScreen.Exercises.createRoute(root = root)) {
                    inclusive = false
                }
            }
        })
    }
}


private fun mapExerciseNameToLeafScreen(exerciseName: ExerciseName): LeafScreen {
    return when (exerciseName) {
        ExerciseName.EXTRA_NUMBER -> LeafScreen.ExtraNumbers
        ExerciseName.EXTRA_WORD -> LeafScreen.ExtraWords
        ExerciseName.NO_NAME -> error("No name screen")
    }
}
