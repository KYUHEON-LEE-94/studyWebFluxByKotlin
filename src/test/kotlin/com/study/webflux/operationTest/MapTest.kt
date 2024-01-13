package com.study.webflux.operationTest

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class MapTest {

    @Test
    fun MapTest1() {
        Flux.range(1, 5)
            .map{it*it}
            .subscribe{ println(it) }
    }

    @Test
    fun MapTest2() {
        Flux.range(1, 10)
            .map{it*it}
            .filter{it%2 == 0}
            .subscribe{println(it)}
    }

}
