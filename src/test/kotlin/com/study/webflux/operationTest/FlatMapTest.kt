package com.study.webflux.operationTest

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers.parallel
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

class FlatMapTest {

    @Test
    @DisplayName("get every 1 sec")
    fun flatMapTest() {
        val employeeIds =  listOf("1", "2", "3", "4", "5", "6", "7", "8")

        val employeeName = Flux.fromIterable(employeeIds)
            .flatMap { id -> getEmployeeDetails(id) } //DB or external service call that return a flux or Mono
            .log()

        StepVerifier.create(employeeName)
            .expectNextCount(8)
            .verifyComplete()
    }

    @Test
    @DisplayName("do parallel")
    fun flatMapUsingParallelScheduler() {
        val employeeIds =  listOf("1", "2", "3", "4", "5", "6", "7", "8")

        val employeeName = Flux.fromIterable(employeeIds)//Flux<String>
            .window(3)//Flux<Flux<String>>
            //.concatMap
            //.flatMapSequential
            .concatMap { identifiers -> identifiers.flatMap {
                    id -> getEmployeeDetails(id)
            }.subscribeOn(parallel())}// 병렬 스케줄러 처리
            .log()


        StepVerifier.create(employeeName)
            .expectNextCount(8)
            .verifyComplete()
    }

    private fun getEmployeeDetails(id: String?): Mono<String> {
        val employees = mapOf(
            "1" to "Sam",
            "2" to "Ajay",
            "3" to "Ram",
            "4" to "James",
            "5" to "Jay",
            "6" to "Lion",
            "7" to "Samira",
            "8" to "Aron"
        )

        Thread.sleep(1000) //실제로는 비동기적인 호출을 통해 대기하지 않고도 데이터 GET
        return employees.getOrDefault(id, "No found").toMono()
    }


}
