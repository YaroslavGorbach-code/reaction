package yaroslavgorbach.reaction.data.exercise.extranumber.local.model

data class NumberPack(val numbers: List<Number>) {
    companion object {
        val Empty = NumberPack(emptyList())
    }
}