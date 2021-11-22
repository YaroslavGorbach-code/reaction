package yaroslavgorbach.reaction.data.training.model

data class Training(val exercises: List<TrainingExercise>) {

    companion object {
        val Test = Training(
            listOf(
                TrainingExercise.Test,
                TrainingExercise.Test,
                TrainingExercise.Test,
                TrainingExercise.Test
            )
        )
    }

    val isFinished: Boolean
        get() = exercises.all { it.isFinished }

    val progressFloat: Float
        get() = (((exercises.sumOf { it.progressInt } / exercises.size)) / 100f)

    val progressString: String
        get() = "${(progressFloat * 100).toInt()} %"
    // TODO: 11/21/2021 replace mock

    val date: String
        get() = "21.11"
}
