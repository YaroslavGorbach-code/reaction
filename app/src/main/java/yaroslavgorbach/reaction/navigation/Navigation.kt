package yaroslavgorbach.reaction.navigation

sealed class NavigationDestination(val destination: String) {
    object ExercisesScreen : NavigationDestination("ExercisesScreen")
    object DescriptionScreen : NavigationDestination("DescriptionScreen")
    object ExtraNumberScreen : NavigationDestination("ExtraNumberScreen")
}

