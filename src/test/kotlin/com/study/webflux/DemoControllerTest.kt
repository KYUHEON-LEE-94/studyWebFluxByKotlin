package com.study.webflux

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.test.StepVerifier
import reactor.test.StepVerifier.Step
import java.time.Duration

/**
 * packageName    : com.study.webflux
 * fileName       : DemoControllerTest
 * author         : LEE KYUHEON
 * date           : 2024-01-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-14        LEE KYUHEON       최초 생성
 */
@WebFluxTest
class DemoControllerTest{


    @Autowired
    lateinit var webTestClient:WebTestClient

    @Test
    fun `flux API test 1`(){
        webTestClient.get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(Int::class.java)
            .hasSize(5)
    }

    @Test
    fun `flux API test 2`(){
        val response = webTestClient.get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult(Int::class.java)
            .responseBody

        StepVerifier.create(response)
            .expectNext(1,2,3,4,5)
            .verifyComplete()
    }

    @Test
    fun `flux API test 3`(){
        webTestClient = webTestClient.mutate() // 설정 복제 -> 새로운 객체를 생성
            .responseTimeout(Duration.ofSeconds(6)) // 타임아웃을 6초로
            .build()

        val response = webTestClient.get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
    }
}