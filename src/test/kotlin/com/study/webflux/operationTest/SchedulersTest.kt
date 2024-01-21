package com.study.webflux.operationTest

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/*operation 이어서*/
class SchedulersTest {


    @Test
    fun defaultThreadingModel() {
        Mono.just("1").log()
            .map{
                log("map", it)
                it.toInt()
            }
            .subscribe(::println)
    }

    @Test
    fun timeOperator() {
        Mono.just("1").log()
            .delayElement(Duration.ofSeconds(1))
            .map{
                log("map", it)
                it.toInt()
            }
            .subscribe(::println)

        Thread.sleep(2000)
    }

    @Test
    fun schedulerExample() {
        Mono.just("1").log()
            .publishOn(Schedulers.fromExecutorService(executorService()))
            .map{
                log("map", it)
                it.toInt()
            }
            .subscribe(::println)
    }

    @Test
    fun subscribeOnExample() {
        Mono.just("1").log()
            .subscribeOn(Schedulers.parallel())
            .map{
                log("map1", it)
                it.toInt()
            }
            .subscribeOn(Schedulers.boundedElastic())
            .subscribeOn(Schedulers.boundedElastic())
            .map{
                log("map2", it)
                it*2
            }

            .subscribe(::println)

        Thread.sleep(1000)
    }

    @Test
    fun subscribeOn1Example() {
        Mono.just("1").log()
            //.subscribeOn(Schedulers.single())
            .map{
                log("map1", it)
                it.toInt()
            }
            .map{
                log("map2", it)
                it*2
            }
            .subscribeOn(Schedulers.parallel())
            .subscribe(::println)
        //subscribe 메서드는 비동기적으로 실행되기 때문에 메인 스레드가 즉시 종료되지 않도록!
        Thread.sleep(1000)
    }

    @Test
    fun runOnExample() {

        println("Number of CPU cores ${Runtime.getRuntime().availableProcessors()}")
        Flux.range(1, 20)
            .parallel()
            .runOn(Schedulers.parallel())
            .map{
                log("map1", it)
                it.toInt()
            }
            .subscribe(::println)
    }



    private fun executorService(): ExecutorService = Executors.newFixedThreadPool(10)
    private fun executorService100(): ExecutorService = Executors.newFixedThreadPool(100)

    /*log which Tread*/
    private fun <T>log(operateName: String, data:T?){
        println("Inside operator $operateName, data is $data, Thread : ${Thread.currentThread().name}")
    }


}
