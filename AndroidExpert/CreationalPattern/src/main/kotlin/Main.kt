fun main() {
//    val myPhone = HandPhone.Builder(processor = "Octa-core")
//        .setBattery("5000MAH")
//        .create()
//    println(myPhone.battery)
    val myPhone = HandPhoneFactory.createHandPhone(Type.NEXUS9)
    println(myPhone.processor)
}