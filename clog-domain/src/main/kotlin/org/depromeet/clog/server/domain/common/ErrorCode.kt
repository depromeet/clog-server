package org.depromeet.clog.server.domain.common

/**
 * ## Clog 서버에서 발생하는 모든 에러 코드의 최상위 인터페이스
 * - 모든 에러 코드는 해당 인터페이스의 구현을 통해 표현되어야 한다.
 * - 한 도메인은 하나의 에러코드를 가지도록 권장된다.
 *     - 이 말인 즉슨, `enum` 클래스로 구현하는 것이 적합하다.
 * - 모든 에러 코드는 에러 코드가 발생하는 도메인의 하위 패키지에 위치해야 한다.
 *     - 예) `org.depromeet.clog.server.domain.user.exception` 패키지에 위치한 `UserErrorCode` 클래스
 *
 * @author kkjsw17
 */
interface ErrorCode {

    val name: String

    val code: String

    val message: String

    val httpStatus: Int
}
