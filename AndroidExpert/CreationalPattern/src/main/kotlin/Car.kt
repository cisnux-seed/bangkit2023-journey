// Singleton Pattern example

data class Car(
    val horsePowers: Int,
)

// first option using object
object CarFactory {
    private val cars = mutableListOf<Car>()

    fun makeCar(horsePowers: Int): Car {
        val car = Car(horsePowers)
        cars.add(car)
        return car
    }
}

// second option using companion object
/**
 * companion object {
 *    @Volatile
 *    private var INSTANCE: TourismDatabase? = null
 *
 *    fun getInstance(context: Context): TourismDatabase =
 *        INSTANCE ?: synchronized(this) {
 *        val instance = Room.databaseBuilder(
 *            context.applicationContext,
 *            TourismDatabase::class.java,
 *            "Tourism.db"
 *        ).build()
 *        INSTANCE = instance
 *        instance
 *    }
 * }
 * */
