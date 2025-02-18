package org.depromeet.cllog.server.domain.common

/**
 * ## Clog 서버에서 발생하는 모든 예외의 최상위 클래스
 * - 모든 커스텀 예외는 이 클래스를 상속받아야 한다.
 * - 모든 커스텀 예외는 예외가 발생하는 도메인의 하위 패키지에 위치해야 한다.
 *     - 예) `org.depromeet.cllog.server.domain.user.exception` 패키지에 위치한 `UserNotFoundException` 클래스
 *
 * @author kkjsw17
 */
abstract class ClogException(
    val errorCode: ErrorCode,
    message: String? = errorCode.message,
) : Exception(message)
