package com.krt.home

import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect1() {

        val observable = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onComplete()
        })

        val observer = object : Observer<Int> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
            }

            override fun onError(e: Throwable) {
            }
        }
        observable.subscribe(observer)
    }

    /**
     * Consumer  会把Consumer重新分装成一个  LambdaObserver  观察者类
     */
    @Test
    fun addition_isCorrect2() {
        val observable = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onComplete()
        })

        val consumer = Consumer<Int> { t -> println("22222222222    $t") }

        observable.subscribeOn(Schedulers.newThread())
                .subscribe(consumer)
    }


    /**
     * map
     */
    @Test
    fun addition_isCorrect3() {
        val observable = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
        })

        val consumer = Consumer<String> { t -> println("2222222222222222    $t") }

        observable.map {
            it.toString()
        }.subscribe(consumer)

    }

    /**
     * flatMap
     */
    @Test
    fun addition_isCorrect4() {
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onNext(4)
        }).concatMap {
            val list = ArrayList<String>()
            for (i in 0..3) {
                list.add("I am value $i");
            }
            Observable.fromIterable(list).delay(10, TimeUnit.SECONDS)
        }.subscribe {
            println("222222222   $it")
        }
    }

    /**
     * zip
     */
    @Test
    fun addition_isCorrect5() {
        val observable1 = Observable.create(ObservableOnSubscribe<Int> {
            println("emit 1")
            it.onNext(1)
            Thread.sleep(1000)

            println("emit 2")
            it.onNext(2)
            Thread.sleep(1000)

            println("emit 3")
            it.onNext(3)
            Thread.sleep(1000)

            println("emit 4")
            it.onNext(4)
            Thread.sleep(1000)

            println("emit 5")
            it.onComplete()
        }).subscribeOn(Schedulers.io())

        val observable2 = Observable.create(ObservableOnSubscribe<String> {
            println("emit A")
            it.onNext("A")
            Thread.sleep(1000)

            println("emit B")
            it.onNext("B")
            Thread.sleep(1000)

            println("emit C")
            it.onNext("C")
            Thread.sleep(1000)

            println("emit D")
            it.onNext("D")
            Thread.sleep(1000)

            println("emit E")
            it.onComplete()
        }).subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2,
                BiFunction<Int, String, String> { t1, t2 ->
                    t1.toString() + t2
                }).subscribe {
            //            println("2222222   $it")
        }

    }

    /**
     * single    代码上发送了两次数据，其实只接受一次。第一次发完了，就关闭了发送功能
     */
    @Test
    fun addition_isCorrect6() {
        Single.create<Int> {
            it.onSuccess(1)
            it.onSuccess(2)
        }.subscribe({
            //            println("22222222   $it")
        }, {

        })
    }


    /**
     * Completable   其实就是
     */
    @Test
    fun addition_isCorrect7() {
        Completable.fromAction {
            println("Hello World")
        }.subscribe()

        Completable.fromAction {

        }.andThen {
            println("123321")
            it.onComplete()
        }.subscribe {
            println("1234567890")
        }
    }

    /**
     * Maybe
     */
    @Test
    fun addition_isCorrect8() {
        Maybe.create<Int> {
            //            it.onComplete()
            it.onSuccess(1)
        }.subscribe({
            println("123123123    $it")
        }, {
            println("1231231asd23    $it")
        }, {
            println("1231231dfsaf23    ")
        })
    }
}

