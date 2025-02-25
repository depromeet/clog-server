package org.depromeet.clog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["org.depromeet.clog.server"])
@EntityScan(basePackages = ["org.depromeet.clog.server.domain", "org.depromeet.clog.server.infrastructure"])
@EnableJpaRepositories(basePackages = ["org.depromeet.clog.server"])
class ClogApplication

fun main(args: Array<String>) {
    runApplication<ClogApplication>(*args)
}
