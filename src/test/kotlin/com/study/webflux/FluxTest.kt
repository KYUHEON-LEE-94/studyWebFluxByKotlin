package com.study.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

/*어떻게 Junit에서 테스트하는가?*/
class FluxTest {

    @Test
    fun flux1() {
        val flux = Flux.just("A", "B", "C")
            .log()

        StepVerifier.create(flux)
            .expectNext("A" )
            .expectNext("B")
            .expectNext("C")
            .verifyComplete() //이 메서드를 사용해야 Flux trigger 할 수 있다.
    }

    @Test
    fun flux2() {
        val flux = Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Error occurred")))
            .log()

        StepVerifier.create(flux)
            .expectNext("A", "B", "C")
            .expectErrorMessage("Error occurred")
            //.expectError(RuntimeException::class.java)
            .verify() //error 때문에 not completed 될것
    }
}
