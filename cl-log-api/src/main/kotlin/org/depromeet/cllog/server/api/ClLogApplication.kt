package org.depromeet.cllog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.depromeet.cllog.server"])
@EntityScan(basePackages = ["org.depromeet.cllog.server.domain"]) // ✅ 엔티티 스캔 추가
@EnableJpaRepositories(basePackages = ["org.depromeet.cllog.server.domain"]) // ✅ JPA Repository 스캔 추가
class ClLogApplication

fun main(args: Array<String>) {
    runApplication<ClLogApplication>(*args)
}

