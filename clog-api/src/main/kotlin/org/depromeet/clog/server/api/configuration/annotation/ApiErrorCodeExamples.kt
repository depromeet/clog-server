package org.depromeet.clog.server.api.configuration.annotation

import org.depromeet.clog.server.domain.common.ErrorCode

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorCodeExamples(
    val value: Array<ErrorCode>,
)
