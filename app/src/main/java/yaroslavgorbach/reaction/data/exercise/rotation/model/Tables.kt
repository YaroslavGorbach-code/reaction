package yaroslavgorbach.reaction.data.exercise.rotation.model

data class Tables(val firstTable: Table, val secondTable: Table, val areTheSame: Boolean) {

    companion object {
        val Test = Tables(Table.Test, Table.Test, false)
    }
}
