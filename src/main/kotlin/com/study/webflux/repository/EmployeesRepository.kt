package com.study.webflux.repository

import com.study.webflux.model.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

/**
 *packageName    : com.study.webflux.repository
 * fileName       : EmployeesRepository
 * author         : LEE KYUHEON
 * date           : 2024-01-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-20        LEE KYUHEON       최초 생성
 */
@Repository
interface EmployeesRepository: ReactiveMongoRepository<Employee, String>