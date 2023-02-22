package domain.model

data class User(val username: String, val email: String, val age: Int? = null)

//data class User(val username: String, val email: String) {
//    var age: Int? = null
//        private set
//
//    constructor(username: String, email: String, age: Int?) : this(username, email) {
//        this.age = age
//    }
//
//    fun copy(username: String = this.username, email: String = this.email, age: Int? = this.age) =
//        User(username, email, age)
//}

