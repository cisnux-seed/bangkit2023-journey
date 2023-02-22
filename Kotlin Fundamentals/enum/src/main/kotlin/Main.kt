import colors.Colors
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    println("""
        1. Red
        2. Blue
        3. Green
    """.trimIndent())
    print("Choose your favorite color\t:")
    val color = when (scanner.nextInt()) {
        1 -> Colors.RED
        2 -> Colors.BLUE
        else -> Colors.GREEN
    }
    println("${when(color){
        Colors.RED -> "your favorite color is Red"
        Colors.BLUE -> "your favorite color is Blue"
        else -> "your color is Green"
    }} \uD83D\uDCA5")
}