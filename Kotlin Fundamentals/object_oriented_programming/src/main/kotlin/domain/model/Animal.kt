package domain.model

// assign null to the variable to make it optional
class Animal(val name: String, weight: Double, age: Int, isMammal: Boolean = false, val color: String? = null) {
    val weight: Double
    val age: Int
    var isMammal: Boolean

    init {
        this.weight = if (weight < 0) 0.1 else weight
        this.age = if (age < 0) 0 else age
        this.isMammal = false
    }
}

//class Animal(name: String, weight: Double, age: Int) {
//    val name: String
//    val weight: Double
//    val age: Int
//    var isMammal: Boolean
//    var color: String? = null
//        private set
//
//    init {
//        this.weight = if (weight < 0) 0.1 else weight
//        this.age = if (age < 0) 0 else age
//        this.name = name
//        this.isMammal = false
//    }
//
//    // secondary constructor is optional
//    constructor(name: String, weight: Double, age: Int, isMammal: Boolean, color: String?) : this(name, weight, age) {
//        this.isMammal = isMammal
//        this.color = color
//    }
//
//    constructor(name: String, weight: Double, age: Int, isMammal: Boolean) : this(name, weight, age) {
//        this.isMammal = isMammal
//    }
//
//    constructor(name: String, weight: Double, age: Int, color: String?) : this(name, weight, age) {
//        this.color = color
//    }
//}