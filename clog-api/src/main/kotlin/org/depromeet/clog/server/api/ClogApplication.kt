package org.depromeet.clog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("org.depromeet.clog.server")
@SpringBootApplication
class ClogApplication

fun main(args: Array<String>) {
    runApplication<ClogApplication>(*args)
}
