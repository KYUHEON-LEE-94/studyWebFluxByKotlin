package com.study.webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 *packageName    : com.study.webflux.model
 * fileName       : Employee
 * author         : LEE KYUHEON
 * date           : 2024-01-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-20        LEE KYUHEON       최초 생성
 */
@Document("employees")
data class Employee (
    @Id
    val id: String?,
    val name: String,
    val dept: String
    )