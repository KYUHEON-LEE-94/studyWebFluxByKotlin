package com.study.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.extra.retry.retryExponentialBackoff
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import java.time.Duration

class RetryDemoTest {

    @Test
    fun `fixed number of retry`() {
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retry(3)
            .subscribe()
    }

    @Test
    fun `retry with fixed delay`() {
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
            .subscribe()

        Thread.sleep(10000)
        /*parallel Thread로 Error 구독함*/
    }

    @Test
    fun `retry with minimum backoff`() {
        /*retry 시간 간격이 조금씩 늘어남 2의n*/
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
            .subscribe()

        Thread.sleep(20000)
    }

    @Test
    fun `retry with exponential backoff`() {
        /*retry 시간 간격이 조금씩 늘어남 2의n*/
        Mono.error<Exception>(RuntimeException("Some exception")).log()
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)).maxBackoff(Duration.ofSeconds(4)).jitter(0.5))
            .subscribe()

        Thread.sleep(20000)
    }
}
