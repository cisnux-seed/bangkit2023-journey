// Dependency Injection Pattern example

class CarEngine(private val engine: Engine) {
    fun start() {
        engine.start()
    }
}

class Engine {
    fun start() {}
}
