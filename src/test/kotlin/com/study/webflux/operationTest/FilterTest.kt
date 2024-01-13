package com.study.webflux.operationTest

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FilterTest {

    @Test
    fun filterTest1() {
        val cities = listOf("Seoul", "Busan", "Ulsan", "Daegu", "Jeju")

        val fluxCities = Flux.fromIterable(cities)

        val filteredCities = fluxCities.filter { city -> city.length < 5 }

        StepVerifier.create(filteredCities.log())
            .expectNext("Jeju")
            .verifyComplete()
    }


        @Test
        fun filterTest2() {
            val cities = listOf("Seoul", "Busan", "Ulsan","Daegu", "Jeju")

            val fluxCities = Flux.fromIterable(cities)

            val filteredCities = fluxCities.filter{city -> city.startsWith("S")}

            StepVerifier.create(filteredCities.log())
                .expectNext("Seoul")
                .verifyComplete()
        }


    }
