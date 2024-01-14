package com.study.webflux.operationTest

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.SignalType
import reactor.test.StepVerifier
import java.time.Duration

class ErrorHandlingTest {

    @Test
    fun combineUsingMerge() {
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
            .concatWith(Flux.just("D"))

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectError()
            .verify()
    }

    @Test
    fun doOnErrorTest(){
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
                //Error 발생시, 때 특정 동작을 수행할 수 있음
            .doOnError{
                error -> println("Some error occurred $error")
            }

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectError()
            .verify()
    }

    @Test
    fun onErrorReturn(){
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
            //error 발생시, 지정값 return
            .onErrorReturn("some default value")

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectNext("some default value")
            .verifyComplete()
    }

    @Test
    fun onErrorResume(){
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some error")))
            //error 발생시, 다른 Flux 나 Mono로 대체 가능
            .onErrorResume {
                println("Some error occureed ${it.message}")
                Flux.just("D")
            }

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectNext("D")
            .verifyComplete()
    }

    @Test
    fun onErrorMap(){
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Business exception")))
            .onErrorMap {
                when (it.message) {
                    "Business exception" -> CustomException("Translation Exception")
                    else -> it
                }
            }

        StepVerifier.create(flux.log())
            .expectNext("A", "B", "C")
            .expectErrorMessage("Translation Exception")
            .verify()
    }

    @Test
    fun doFinallyTest(){
        val flux = Flux.just("A", "B", "C")
            .delayElements(Duration.ofSeconds(1))
            .doFinally{
                when{
                    it == SignalType.CANCEL -> println("Perform operation on cancel")
                    it == SignalType.ON_COMPLETE -> println("Perform operation on complete")
                    it == SignalType.ON_ERROR -> println("Perform operation on onerror")
                }
            }
            .log()
            .take(2) //Elements 3개지만 2개만 take해서 finally 실행 여부 확인

        StepVerifier.create(flux)
            .expectNext("A", "B")
            .thenCancel()
            .verify()
    }

    class CustomException(errorMessage:String):Throwable(errorMessage)
}
