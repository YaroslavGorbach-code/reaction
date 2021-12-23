package yaroslavgorbach.reaction.utill

fun <T> Collection<T>.firstOr(default: T) = firstOrNull() ?: default
