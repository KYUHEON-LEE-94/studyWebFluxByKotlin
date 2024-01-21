package com.study.webflux.controller

import com.study.webflux.model.Employee
import com.study.webflux.repository.EmployeesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *packageName    : com.study.webflux.controller
 * fileName       : EmployeeController
 * author         : LEE KYUHEON
 * date           : 2024-01-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-20        LEE KYUHEON       최초 생성
 */
@RestController
@RequestMapping("v1")
class EmployeeController(
    @Autowired
    val employeesRepository: EmployeesRepository
) {

    @GetMapping("/employees")
    fun getAllEmployees(): Flux<Employee> {
        return employeesRepository.findAll()
    }

    @GetMapping("{id}")
    fun getEmployeeById(@PathVariable id: String): Mono<Employee> {
        return employeesRepository.findById(id)
    }

    @PostMapping("/employees")
    fun save(@RequestBody employee: Employee): Mono<Employee> {
        return employeesRepository.save(employee)
    }

    @PutMapping("/update")
    fun update(@RequestBody employee: Employee): Mono<Employee> {
        return employeesRepository.save(employee)
    }

    @DeleteMapping
    fun delete(): Mono<Void> {
        return employeesRepository.deleteAll()
    }

    @DeleteMapping("/employees/{id}")
    fun delete(@PathVariable id:String): Mono<Void> {
        println("id = $id")
        return employeesRepository.deleteById(id)
    }
}