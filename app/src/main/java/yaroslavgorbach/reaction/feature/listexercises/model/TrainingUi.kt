package yaroslavgorbach.reaction.feature.listexercises.model

import yaroslavgorbach.reaction.data.training.model.Training

data class TrainingUi(val trainings: List<Training>) {
    companion object {
        val Test = TrainingUi(
            listOf(
                Training.Test,
                Training.Test,
                Training.Test,
                Training.Test,
                Training.Test,
                Training.Test
            )
        )
    }
}