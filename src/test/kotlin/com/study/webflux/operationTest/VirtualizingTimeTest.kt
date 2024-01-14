package com.study.webflux.operationTest

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration
/*operation 이어서*/
class VirtualizingTimeTest {


    @Test
    fun `flux test without virtual time scheduler`() {
        val flux = Flux.interval(Duration.ofSeconds(1))
            .take(3)

        StepVerifier.create(flux.log())
            .expectNext(0,1,2)
            .verifyComplete()
    }

    @Test
    fun `flux test with virtual time scheduler`() {
        VirtualTimeScheduler.getOrSet() //테스트 시간 가속화

        val flux = Flux.interval(Duration.ofSeconds(1))
            .take(3)

        StepVerifier.withVirtualTime { flux.log() }
            .thenAwait(Duration.ofSeconds(6))
            .expectNext(0,1,2)
            .verifyComplete()
    }
}
