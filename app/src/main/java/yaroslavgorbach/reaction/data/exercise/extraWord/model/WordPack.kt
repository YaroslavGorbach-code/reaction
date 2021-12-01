package yaroslavgorbach.reaction.data.exercise.extraWord.model

data class WordPack(val words: List<Word>) {
    companion object {
        val Empty = WordPack(emptyList())
    }
}