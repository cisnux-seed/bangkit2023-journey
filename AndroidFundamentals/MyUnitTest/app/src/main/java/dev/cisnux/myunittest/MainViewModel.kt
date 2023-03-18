package dev.cisnux.myunittest

internal class MainViewModel(private val cuboidModel: CuboidModel) {
    // getter in runtime
    val circumference get() = cuboidModel.circumference
    val surfaceArea get() = cuboidModel.surfaceArea
    val volume get() = cuboidModel.volume

    fun save(w: Double, l: Double, h: Double) {
        cuboidModel.save(w, l, h)
    }
}