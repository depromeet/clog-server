package org.depromeet.clog.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.TimeZone

@SpringBootApplication
@ComponentScan(
    basePackages = ["org.depromeet.clog.server"],
    excludeFilters = [
        ComponentScan.Filter(type = FilterType.REGEX, pattern = ["org\\.depromeet\\.clog\\.server\\.admin\\..*"])
    ]
)
@EnableScheduling
class ClogApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<ClogApplication>(*args)
}
