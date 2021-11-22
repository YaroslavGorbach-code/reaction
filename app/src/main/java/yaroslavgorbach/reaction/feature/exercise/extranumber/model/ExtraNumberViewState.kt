package yaroslavgorbach.reaction.feature.exercise.extranumber.model

import yaroslavgorbach.reaction.data.exercise.extranumber.local.model.NumberPack

data class ExtraNumberViewState(
    val numberPacks: List<NumberPack> = emptyList(),
    val progress: Float = 0f,
    val countDownTime: Int = 0
) {
    companion object {
        val Empty = ExtraNumberViewState()
    }
}
