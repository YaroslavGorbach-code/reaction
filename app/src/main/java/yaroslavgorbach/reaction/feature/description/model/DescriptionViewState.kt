package yaroslavgorbach.reaction.feature.description.model

import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class DescriptionViewState(
    val descriptionRes: Int = R.string.empty,
    val exerciseName: ExerciseName = ExerciseName.EXTRA_NUMBER,
) {
    companion object {
        val Empty = DescriptionViewState()
    }
}
