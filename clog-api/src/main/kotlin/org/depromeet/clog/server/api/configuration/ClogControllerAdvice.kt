package org.depromeet.clog.server.api.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.apache.coyote.BadRequestException
import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.common.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.support.MissingServletRequestPartException

@RestControllerAdvice
class ClogControllerAdvice {

    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(ClogException::class)
    fun handleClogException(e: ClogException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "ClogException 발생: ${e.message}" }
        return ResponseEntity.status(e.errorCode.httpStatus)
            .body(ErrorResponse.from(e.errorCode, e.detail))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e) { "Exception 발생: ${e.message}" }
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR

        return ResponseEntity.status(errorCode.httpStatus)
            .body(ErrorResponse.from(errorCode, null))
    }

    @ExceptionHandler(InsufficientAuthenticationException::class)
    fun handleInsufficientAuthenticationException(
        e: InsufficientAuthenticationException
    ): ResponseEntity<ErrorResponse> {
        logger.error(e) { "InsufficientAuthenticationException 발생: ${e.message}" }
        val errorCode = ErrorCode.TOKEN_INVALID

        return ResponseEntity.status(errorCode.httpStatus)
            .body(ErrorResponse.from(errorCode, null))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        value = [
            MethodArgumentNotValidException::class,
            BindException::class,
            ConstraintViolationException::class,
            MissingServletRequestParameterException::class,
            MissingServletRequestPartException::class,
            IllegalArgumentException::class,
            HttpMessageNotReadableException::class,
            BadRequestException::class,
        ]
    )
    fun handleBadRequest(request: HttpServletRequest, e: Exception): ErrorResponse {
        val reason = buildInvalidRequestReason(e)

        logger.warn(e) { "잘못된 파라미터 입력 발생: $reason, url: ${request.requestURL}" }
        return ErrorResponse.from(ErrorCode.INVALID_REQUEST, mapOf("reason" to reason))
    }

    private fun buildInvalidRequestReason(e: Exception): List<String> {
        return when (e) {
            is MethodArgumentNotValidException ->
                e.bindingResult.extractErrorDetail().ifEmpty { buildDefaultMessage(e) }

            is BindException ->
                e.bindingResult.extractErrorDetail().ifEmpty { buildDefaultMessage(e) }

            is ConstraintViolationException ->
                e.constraintViolations.map { "${it.propertyPath} -> ${it.message}" }.ifEmpty { buildDefaultMessage(e) }

            is MissingServletRequestParameterException -> listOf("${e.parameterName} 필수 파라미터가 누락되었습니다.")

            is MissingServletRequestPartException -> listOf("${e.requestPartName} 필수 파트가 누락되었습니다.")

            else -> buildDefaultMessage(e)
        }
    }

    private fun buildDefaultMessage(e: Exception): List<String> {
        return if (e.message.isNullOrBlank()) {
            listOf("잘못된 요청입니다. 자세한 내용은 서버팀에 문의 주세요.")
        } else {
            listOf(e.message!!)
        }
    }

    private fun BindingResult.extractErrorDetail(): List<String> =
        this.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
}
