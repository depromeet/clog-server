package org.depromeet.clog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.TimeZone

@SpringBootApplication(scanBasePackages = ["org.depromeet.clog.server"])
@EnableScheduling
class ClogApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<ClogApplication>(*args)
}
