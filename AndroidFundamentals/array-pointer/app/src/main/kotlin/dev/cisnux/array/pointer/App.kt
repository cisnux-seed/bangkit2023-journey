/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package dev.cisnux.array.pointer

data class User(var firstName: String, var lastName: String)

fun main() {
//    instance of class is non-primitive data type
//    Instance of class
//    val users1 = mutableListOf<User>()
//    repeat(5) {
//        users1.add(User("$it Fajra", "Risqulla"))
//    }
//    users1.forEach(::println)
//    println()
//    // val users2 = users1
//    // to create a new list we need to use mutableListOf and call copy method
//    val users2 = mutableListOf<User>(*(users1.map { it.copy() }).toTypedArray())
//    users2[2].lastName = "Cisnux"
//    users2.forEach(::println)
//    println()
//    users1.forEach(::println)

//    String is primitive data type
//    String
    val names1 = mutableListOf<String>()
    repeat(5) {
        names1.add("$it Fajra Risqulla")
    }
    println(names1)
    println()
//    val names2 = names1
    val names2 = mutableListOf(*names1.toTypedArray())
    names2[0] = "Cisnux"
    println(names1)
    println()
    println(names2)
    println()
}
