package org.depromeet.clog.server.api.configuration

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ClogControllerAdvice {
    @ExceptionHandler(ClogException::class)
    fun handleClogException(e: ClogException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(e.errorCode.httpStatus)
            .body(ErrorResponse.from(e.errorCode, e.detail))
    }
}
