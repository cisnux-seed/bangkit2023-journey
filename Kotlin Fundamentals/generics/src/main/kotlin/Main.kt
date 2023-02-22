fun main(args: Array<String>) {
    val data = mutableListOf(3,2,4).also {
        println(it.addNumber(2))
    }
}

// Covariant
//interface List<out E> : Collection<E> {
//    operator fun get(index: Int): E
//}

// Contravariant
//interface Comparable<in T> {
//    operator fun compareTo(other: T): Int
//}

fun <T : Number> List<T>.addNumber(value: T): T {
    @Suppress("UNCHECKED_CAST")
    return (this[0].toInt() + value.toInt()) as T
}