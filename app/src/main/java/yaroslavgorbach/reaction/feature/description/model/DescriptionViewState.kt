package yaroslavgorbach.reaction.feature.description.model

import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listExercises.local.model.ExerciseName

data class DescriptionViewState(
    val descriptionRes: Int = R.string.empty,
    val exerciseName: ExerciseName = ExerciseName.NO_NAME,
) {
    companion object {
        val Empty = DescriptionViewState()
    }
}
