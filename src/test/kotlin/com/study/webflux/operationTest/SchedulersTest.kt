package com.study.webflux.operationTest

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration
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

    private fun log(operateName: String, data:String?){
        println("Inside operator $operateName, data is $data, Thread : ${Thread.currentThread().name}")
    }

}
