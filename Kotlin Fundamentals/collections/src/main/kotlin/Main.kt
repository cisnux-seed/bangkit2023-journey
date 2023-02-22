import kotlin.system.measureTimeMillis

fun main() {
    //  eager evaluation->horizontal evaluation
    val firstScore = measureTimeMillis {
        val list = (1..1000000).toList()
        list.filter { it % 5 == 0 }.map { it * 2 }.forEach { println(it) }
    }
    //  lazy evaluation->vertical evaluation
    val secondScore = measureTimeMillis {
        val list = (1..1000000).toList()
        list.asSequence().filter { it % 5 == 0 }.map { it * 2 }.forEach { println(it) }
    }

    // lazy evaluation is faster than eager evaluation
    println(
        """
        first score: $firstScore
        second score: $secondScore
    """.trimIndent()
    )
}