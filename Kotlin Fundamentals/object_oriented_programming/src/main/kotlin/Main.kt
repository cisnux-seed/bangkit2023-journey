import domain.model.Animal
import domain.model.User

fun main() {
    val firstAnimal = Animal(name = "mbul", weight = 5.5, age = 2, isMammal = true)
    val secondAnimal = Animal(name = "ibon", weight = 5.5, age = 2, color = "Red", isMammal = true)
    val thirdAnimal = Animal(name = "choco", weight = 5.5, age = 2, color = "Blue")
    println(firstAnimal.isMammal)
    println(secondAnimal.isMammal)
    println(secondAnimal.color)
    println("Third Animal: ${thirdAnimal.name}, ${thirdAnimal.color}")
    firstAnimal.isMammal = false
    secondAnimal.isMammal = false
//    Cannot assign to this variable because the setter is private
//    secondAnimal.color = "blue"
    println(firstAnimal.isMammal)
    println(secondAnimal.isMammal)
    println(secondAnimal.color)
    val user = User(username = "fajra", email = "fajra@gmail.com")
    val user2 = user.copy(age = 7)
    println(user2.age)
}