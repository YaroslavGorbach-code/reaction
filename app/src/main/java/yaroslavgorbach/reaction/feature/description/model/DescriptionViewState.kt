package yaroslavgorbach.reaction.feature.description.model

import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.listexercises.local.model.ExerciseName

data class DescriptionViewState(
    val descriptionRes: Int = R.string.test_description,
    val exerciseName: ExerciseName = ExerciseName.TEST,
) {
    companion object {
        val Empty = DescriptionViewState()
    }
}
