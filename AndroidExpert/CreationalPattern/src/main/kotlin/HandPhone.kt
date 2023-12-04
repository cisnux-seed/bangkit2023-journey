// Builder Pattern example

class HandPhone private constructor(builder: Builder){
    val processor = builder.processor
    val battery = builder.battery
    val screenSize = builder.screenSize

    class Builder(processor: String) {
        var battery = "4000MAH"
            private set
        var screenSize = "6inch"
            private set
        var processor = processor
            private set

        fun setBattery(battery: String): Builder {
            this.battery = battery
            return this
        }

        fun setScreenSize(screenSize: String): Builder {
            this.screenSize = screenSize
            return this
        }

        fun create(): HandPhone{
            return HandPhone(this)
        }
    }
}