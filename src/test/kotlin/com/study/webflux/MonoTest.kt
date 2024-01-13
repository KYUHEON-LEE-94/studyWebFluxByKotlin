package com.study.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MonoTest {

    @Test
    fun mono1() {
        //Mono 0 or 1 item
        Mono.just("A")
            .log()
            .subscribe { data -> println("My data is $data") }
    }

    @Test
    fun mono2() {
        //Mono 0 or 1 item
        Mono.error<Exception>(Exception("Some exception"))
            .log()
            .doOnError{error -> println("Error occurred ${error.message}") }
            .subscribe()
    }

    @Test
    fun junitTest1() {
        val mono = Mono.just("A").log()

        StepVerifier.create(mono)
            .expectNext("A")
            .verifyComplete()
    }

    @Test
    fun junitTest2() {
        val mono = Mono.error<Exception>(Exception("Some exception"))

        StepVerifier.create(mono)
            .expectError(Error::class.java)
            .verify()
    }
}
