fun main() {
    // the power of type inference
    val rangeInt = (1..10 step 2).onEach { item ->
        println(item)
    }
    println(rangeInt)
    println(rangeInt.toList())

    for (item in 1..100 step 2)
        println("$item. I love this \uD83D\uDCAF")

    for (item in 99 downTo 1 step 2)
        println("$item. I love this \uD83D\uDCAF")
}