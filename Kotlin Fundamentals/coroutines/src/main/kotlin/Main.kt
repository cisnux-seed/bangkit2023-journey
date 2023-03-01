import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.system.measureTimeMillis

/**
 * Coroutines Builder
 * - launch -> return job
 * - runBlocking
 * - async -> return deferred
 * */
// - runBlocking and launch
//fun main() = runBlocking {
//    launch {
//        delay(1000L)
//        println("Coroutines!")
//    }
//    print("Hello, ")
//    delay(2000L)
//}

//- async
fun main() = runBlocking {
    val timeOne = measureTimeMillis {
        val capital = getCapital()
        val income = getIncome()
        println("Your profit is ${income - capital}")
    }

    val timeTwo = measureTimeMillis {
        val capital = async { getCapital() }
        val income = async { getIncome() }
        println("Your profit is ${income.await() - capital.await()}")
    }

    println("Completed in $timeOne ms vs $timeTwo ms")
}

suspend fun getCapital(): Int {
    delay(1000L)
    return 50000
}

suspend fun getIncome(): Int {
    delay(1000L)
    return 75000
}

// - job
//@InternalCoroutinesApi
//fun main() = runBlocking {
//    val job = launch {
//        delay(5000)
//        println("Start new job!")
//    }
//
//    delay(2000)
//    job.cancel(cause = CancellationException("time is up!"))
//    println("Cancelling job...")
//    if (job.isCancelled){
//        println("Job is cancelled because ${job.getCancellationException().message}")
//    }
//}
//fun main() = runBlocking {
//    // with CoroutineStart.LAZY the job can only be executed when start or join function are called
//    val job = launch(start = CoroutineStart.LAZY) {
//        delay(1000L)
//        println("Start new job!")
//    }
//
//    // use the start function to run a job before the job is completed
//    job.start()
//    // use the join function to run a job after the job is completed
////    job.join()
//    println("Other task")
//}

// - Dispatchers
// Dispatcher.Default -> with this dispatcher, we can have the number of threads the same as cpu's threads
// Dispatcher.IO
// Dispatcher.Unconfined
//fun main() = runBlocking<Unit> {
//    launch(Dispatchers.Unconfined) {
//        println("Starting in ${Thread.currentThread().name}")
//        delay(1000)
//        println("Resuming in ${Thread.currentThread().name}")
//    }.start()
//}

/**
 * - ThreadBuilder:
 * - Single thread Context
 * - Thread Pool
* */
//@OptIn(ObsoleteCoroutinesApi::class)
//fun main() = runBlocking<Unit> {
//    val dispatcher = newSingleThreadContext("myThread")
//    launch(dispatcher) {
//        println("Starting in ${Thread.currentThread().name}")
//        delay(1000)
//        println("Resuming in ${Thread.currentThread().name}")
//    }.start()
//}

//@OptIn(ObsoleteCoroutinesApi::class)
//fun main() = runBlocking<Unit> {
//    val dispatcher = newFixedThreadPoolContext(3, "myPool")
//
//    launch(dispatcher) {
//        println("Starting in ${Thread.currentThread().name}")
//        delay(1000)
//        println("Resuming in ${Thread.currentThread().name}")
//    }.start()
//}

/**
 * use [Channel] to sharing resources between the coroutines
 * */
//fun main() = runBlocking(CoroutineName("main")) {
//    val channel = Channel<Int>()
//    launch(CoroutineName("v1coroutine")){
//        println("Sending from ${Thread.currentThread().name}")
//        for (x in 1..5) channel.send(x * x)
//    }
//
//    repeat(5) { println(channel.receive()) }
//    println("received in ${Thread.currentThread().name}")
//}