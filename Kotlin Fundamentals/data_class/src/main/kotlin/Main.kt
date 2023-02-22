import data.model.AnotherUser
import data.model.User

fun main() {
    val firstUser = User(
        username = "fajrarisqulla",
        email = "fajrarisqulla@gmail.com",
        age = 20
    )
    val secondUser = firstUser.copy()
    println(firstUser == secondUser)
    val thirdUser = AnotherUser(
        username = "cisnux",
        email = "cisnux@gmail.com",
        age = 18
    )
    val fourthUser = AnotherUser(
        username = "cisnux",
        email = "cisnux@gmail.com",
        age = 18
    )
    val fifthUser = fourthUser
    val sixthUser = firstUser

    println(thirdUser == fourthUser)
    // identical (referential equality)
    /**
     * fifthUser === fourthUser evaluates to true if and only if fifthUser and fourthUser point to the same object
     * */
    println(fifthUser === fourthUser)
    println(sixthUser === firstUser)
}