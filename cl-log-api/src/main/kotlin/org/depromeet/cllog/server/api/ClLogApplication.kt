package org.depromeet.cllog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClLogApplication

fun main(args: Array<String>) {
    runApplication<ClLogApplication>(*args)
}