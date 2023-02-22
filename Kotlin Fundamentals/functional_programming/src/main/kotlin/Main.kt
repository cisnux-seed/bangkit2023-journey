var messages = "Kotlin"

fun main() {
    val someNumbers = intArrayOf(1, 2, 4)
    val anotherNumbers = mutableListOf(1, 2, 7)
    println(calculate(1, 2, 3, 4))
    println(calculate(1, 2, 3))
    println(calculate(1, 2, 3, greeting = "Say hello"))
    println(calculate(*someNumbers))
    println(calculate(*(anotherNumbers).toIntArray()))
    10.printInt()
    println(10.slice)
    val num: Int? = null
    println(num.slice)
    println(sum?.invoke(7, 3))
    printMessage("Hi")
    printResult(2, sumFunction)
    // inline function
    printResult(10) { value ->
        value + value
    }
    printResult(20) { value ->
        value + value
    }

    val message = buildString {
        append("Hello ")
        append("from ")
        append("lambda ")
    }

    println(message)
    println(sumReflection(7, 4))
    val numbers = 1..10
    val evenNumbers = numbers.filter(::isEvenNumber)
    println(evenNumbers)

    println(::messages.name)
    println(::messages.get())
    ::messages.set("Kotlin Academy")
    println(::messages.get())

    println("the factorial of 3 is ${calculateFactorial(3)}")
}

fun isEvenNumber(number: Int) = number % 2 == 0

val sumReflection: (Int, Int) -> Int = ::count
fun count(
    valueA: Int, valueB
    : Int
): Int {
    return valueA + valueB
}

// a function can only have one vararg argument
// the vararg argument cannot be used with named arguments
fun calculate(vararg numbers: Int): Int {
    println(numbers.toList())
    return numbers.sum()
}

fun calculate(vararg numbers: Int, greeting: String): Int {
    println(numbers.toList())
    println(greeting)
    return numbers.sum()
}

// extension functions
fun Int.printInt() {
    println("value $this")
}

// extension properties
//val Int.slice: Int
//    get() = this / 2

// nullable receive
//val Int?.slice: Int
//    get() = if (this == null) 0 else this.div(2)

val Int?.slice: Int
    get() = this?.div(2) ?: 0

// function type
//typealias Arithmetic = (Int, Int) -> Int
//
//val sum: Arithmetic = { valueA, valueB -> valueA + valueB }
//
//val multiply: Arithmetic = { valueA, valueB -> valueA * valueB }

typealias Arithmetic = ((Int, Int) -> Int)?

// val sum: Arithmetic = null
val sum: Arithmetic = { valueA, valueB -> valueA + valueB }

// lambda expression
val printMessage = { message: String -> println(message) }

// higher order function
inline fun printResult(value: Int, sum: (Int) -> Int) {
    val result = sum(value)
    println(result)
}

var sumFunction: (Int) -> Int = { value -> value + value }

// lambda with receiver
inline fun buildString(action: StringBuilder.() -> Unit): String {
    val stringBuilder = StringBuilder()
//    action()
    stringBuilder.action()
    return stringBuilder.toString()
}

// recursion function
// tailrec can be called outside try-catch-finally block
tailrec fun calculateFactorial(n: Int, result: Int = 1): Int {
    val newResult = n * result
    return if (n == 1)
        newResult
    else calculateFactorial(n - 1, newResult)
}