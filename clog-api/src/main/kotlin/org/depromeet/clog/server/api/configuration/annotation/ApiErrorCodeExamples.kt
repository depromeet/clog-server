package org.depromeet.clog.server.api.configuration.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorCodeExamples(
    val value: Array<ApiErrorCodeExample>,
)
