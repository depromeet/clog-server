package org.depromeet.clog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.depromeet.clog.server"])
class ClogApplication

fun main(args: Array<String>) {
    runApplication<ClogApplication>(*args)
}
