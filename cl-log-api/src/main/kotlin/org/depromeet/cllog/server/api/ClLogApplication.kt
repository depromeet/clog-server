package org.depromeet.cllog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("org.depromeet.cllog.server")
@SpringBootApplication(scanBasePackages = ["org.depromeet.cllog.server"])
class ClLogApplication

fun main(args: Array<String>) {
    runApplication<ClLogApplication>(*args)
}
