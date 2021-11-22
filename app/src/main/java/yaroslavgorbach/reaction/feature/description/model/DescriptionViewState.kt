package yaroslavgorbach.reaction.feature.description.model

import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class DescriptionViewState(
    val descriptionRes: Int = 0,
    val exerciseName: ExerciseName = ExerciseName.TEST,
) {
    companion object {
        val Empty = DescriptionViewState()
    }
}
