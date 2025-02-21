package org.depromeet.cllog.server.api.configuration

import org.depromeet.cllog.server.domain.common.ApiResponse
import org.depromeet.cllog.server.domain.common.ClogException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ClogControllerAdvice {

    @ExceptionHandler(ClogException::class)
    fun handleClLogException(e: ClogException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity.status(e.errorCode.httpStatus)
            .body(ApiResponse.fail(e))
    }
}
