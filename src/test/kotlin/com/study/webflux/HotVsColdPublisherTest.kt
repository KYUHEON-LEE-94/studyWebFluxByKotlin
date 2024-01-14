package com.study.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

/*어떻게 Junit에서 테스트하는가?*/
class HotVsColdPublisherTest {

    @Test
    fun coldPublisherTest() {
        val flux = Flux.just("A", "B", "C", "D")
            .delayElements(Duration.ofSeconds(1))

        flux.subscribe{ println("Subscriber 1 $it") } //emitting data from beginning

        Thread.sleep(2000)

        flux.subscribe{ println("Subscriber 2 $it") } //emitting data from beginning

        Thread.sleep(4000)
    }

    @Test
    fun hotPublisherTest() {
        val flux = Flux.just("A", "B", "C", "D")
            .delayElements(Duration.ofSeconds(1))

        val connectableFlux = flux.publish()

        connectableFlux.connect()

        connectableFlux.subscribe{ println("Subscriber 1 $it")}  //emitting data from previous flux emitted

        Thread.sleep(2000)

        connectableFlux.subscribe{ println("Subscriber 2 $it")} //emitting data from previous flux emitted

        Thread.sleep(4000)
    }
}
