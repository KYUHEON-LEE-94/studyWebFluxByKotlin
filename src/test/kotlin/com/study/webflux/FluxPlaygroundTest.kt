package com.study.webflux

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import java.lang.RuntimeException
import java.time.Duration

class FluxPlaygroundTest {

    @Test
    fun fluxTest() {
        Flux.just("A", "B", "C")
            .log()
            .subscribe { data -> println(data) }
    }

    @Test
    fun fluxWithError() {
        Flux.just("A", "B", "C")
            .concatWith(Flux.error(RuntimeException("Some Exception occurred"))) //not on complete
            .log()
            .subscribe ({ data -> println(data) },
                {error -> println("Error is $error") }, //Error 인 경우 O
                { println("Completed") }) //Error인 경우 안뜸
    }

    @Test
    fun fluxWithIterable() {
        Flux.fromIterable(listOf("A", "B", "C"))
            .log()
            .subscribe { data -> println(data) }
    }

    @Test
    fun fluxWithRange() {
        Flux.range(5, 6)
            .log()
            .subscribe { data -> println(data) }
    }

    @Test
    fun fluxFromInterval() {
        Flux.interval(Duration.ofSeconds(1))
            .log()
            .subscribe { data -> println(data) }
        Thread.sleep(5000)
    }

    @Test
    fun fluxWithTakeOperator() {
        Flux.interval(Duration.ofSeconds(1))
            .log()
            .take(3) //몇개만 take할것인가?
            .subscribe { data -> println(data) }
        Thread.sleep(10000)
    }

    @Test
    fun fluxWithRequest() {

        Flux.range(1,9)
            .log()
            .subscribe ({ data -> println(data) }, {}, {}, {sub -> sub.request(3)})

        Thread.sleep(5000)
    }

    @Test
    fun fluxWithErrorHandling() {
        Flux.just("A","B","C")
            .concatWith(Flux.error(RuntimeException("Some Exception")))
            .onErrorReturn("Some Item on exception")
            .log()
            .subscribe { data -> println(data) }
        Thread.sleep(5000)
    }
}
