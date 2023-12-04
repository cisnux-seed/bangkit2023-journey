import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

fun main() {
    // single thread
    // cold stream
//    Observable.just("1", "2", "3", "4", "5", "6")
//        .map { item -> item.toInt() }
//        .filter { number ->
//            number % 2 == 1
//        }
//        .doOnNext { println("$it adalah bilangan ganjil") }
//        .count()
//        .subscribe { result ->
//            println("Total bilangan ganjil : $result")
//        }

    // multi thread
//    getEmployeeNames()
//        .subscribeOn(Schedulers.io())
//        .observeOn(Schedulers.single())
//        .subscribe { name ->
//            println(name)
//        }

    // hot stream
    // publish subject
//    val source = PublishSubject.create<Int>()
//    println("PublishSubject")
//    source.subscribe {
//        println("first $it")
//    } //Akan mengambil nilai 1, 2, 3 dan onComplete
//    source.onNext(1)
//    source.onNext(2)
//    source.subscribe {
//        println("second $it")
//    } //Akan mengambil nilai 3 dan onComplete
//    source.onNext(3)
//    source.onComplete()

    // behavior subject
//    val source = BehaviorSubject.create<Int>()
//    println("BehaviorSubject")
//    source.subscribe {
//        println("first $it")
//    } //Akan mengambil nilai 1, 2, 3 dan onComplete
//    source.onNext(1)
//    source.onNext(2)
//    source.subscribe {
//        println("second $it")
//    } //Akan mengambil nilai 2, 3 dan onComplete
//    source.onNext(3)
//    source.onComplete()

    // replay subject
//    val source = ReplaySubject.create<Int>()
//    println("ReplaySubject")
//    source.subscribe {
//        println("first $it")
//    } //Akan mengambil nilai 1, 2, 3 dan onComplete
//    source.onNext(1)
//    source.onNext(2)
//    source.subscribe {
//        println("second $it")
//    } //Akan mengambil nilai 1, 2, 3 dan onComplete
//    source.onNext(3)
//    source.onComplete()

    // async subject
    val source = AsyncSubject.create<Int>()
    println("AsyncSubject")
    source.subscribe {
        println("first $it")
    } //Akan mengambil nilai 3 dan onComplete
    source.onNext(1)
    source.onNext(2)
    source.subscribe {
        println("second $it")
    } //Akan mengambil nilai 3 dan onComplete
    source.onNext(3)
    source.onComplete()
}

private fun getEmployeeNames(): Flowable<String> {
    // cold stream
    val name = mutableListOf("Buchori", "Dimas", "Tia", "Gilang", "Widy")
    return Flowable.fromIterable(name) //untuk membuat Observable dari Array
}