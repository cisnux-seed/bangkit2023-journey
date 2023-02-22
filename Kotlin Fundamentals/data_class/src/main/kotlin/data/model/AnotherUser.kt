package data.model

class AnotherUser(
    val username: String,
    val email: String,
    val age: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AnotherUser
        if (username != other.username) return false
        if (email != other.email) return false
        if (age != other.age) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + age
        return result
    }
}