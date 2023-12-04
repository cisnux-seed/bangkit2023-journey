// Factory Pattern example

interface HandPhoneI {
    val processor: String
    val battery: String
    val screenSize: String
}

class HandPhoneNexus5 : HandPhoneI {
    override var processor: String = "Snapdragon"
    override var battery: String = "2300 mAh"
    override var screenSize: String = "4.95 inch"
}

class HandPhoneNexus9 : HandPhoneI {
    override var processor: String = "Nvidia Tegra"
    override var battery: String = "6700 mAh"
    override var screenSize: String = "8.9 inch"
}

enum class Type {
    NEXUS5, NEXUS9
}

class HandPhoneFactory {
    companion object {
        fun createHandPhone(type: Type): HandPhoneI =
            when(type){
                Type.NEXUS5 -> HandPhoneNexus5()
                Type.NEXUS9 -> HandPhoneNexus9()
            }
    }
}