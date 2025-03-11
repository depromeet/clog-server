package org.depromeet.clog.server.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.depromeet.clog.server"])
class ClogAdminApplication

fun main(args: Array<String>) {
    runApplication<ClogAdminApplication>(*args)
}
