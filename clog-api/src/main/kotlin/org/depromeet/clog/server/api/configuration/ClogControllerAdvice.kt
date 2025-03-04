package org.depromeet.clog.server.api.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ClogControllerAdvice {

    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(ClogException::class)
    fun handleClogException(e: ClogException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "ClogException 발생: ${e.message}" }
        return ResponseEntity.status(e.errorCode.httpStatus)
            .body(ErrorResponse.from(e.errorCode, e.detail))
    }
}
