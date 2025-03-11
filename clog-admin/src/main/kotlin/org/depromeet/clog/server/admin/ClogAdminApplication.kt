package org.depromeet.clog.server.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(
    basePackages = ["org.depromeet.clog.server"],
    excludeFilters = [
        ComponentScan.Filter(type = FilterType.REGEX, pattern = ["org\\.depromeet\\.clog\\.server\\.api\\..*"])
    ]
)
class ClogAdminApplication

fun main(args: Array<String>) {
    runApplication<ClogAdminApplication>(*args)
}
