package com.study.webflux

import com.study.webflux.model.Employee
import com.study.webflux.repository.EmployeesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *packageName    : com.study.webflux
 * fileName       : DbSeeder
 * author         : LEE KYUHEON
 * date           : 2024-01-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-20        LEE KYUHEON       최초 생성
 */
@Component
class DbSeeder(
    @Autowired
    val employeesRepository: EmployeesRepository,


    @Autowired
    val reactiveMongoOperations: ReactiveMongoOperations
):CommandLineRunner {

    val employeeList = Flux.just(
        Employee(null, "Jessica", "IT"),
        Employee(null, "Reina", "IT"),
        Employee(null, "Aria", "Medical"),
        Employee(null, "Lucas", "Civil")
    )

    override fun run(vararg args: String?) {
        dbSetup()
    }

    private fun dbSetup() {
        val employees = employeeList.flatMap {
            employeesRepository.save(it)
        }

        /*Employee class 중복 저장 방지*/
        reactiveMongoOperations.collectionExists(Employee::class.java)
            .flatMap {
                when(it){
                    true -> reactiveMongoOperations.dropCollection(Employee::class.java)
                    false -> Mono.empty()
                }
            }
            .thenMany(reactiveMongoOperations.createCollection(Employee::class.java))
            .thenMany(employees)
            .thenMany(employeesRepository.findAll())
            .subscribe({ println(it) }, {error -> println(error) }, { println("Database has been initialized") })
    }
}