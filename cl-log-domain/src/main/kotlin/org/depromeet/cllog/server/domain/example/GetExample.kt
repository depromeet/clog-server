package org.depromeet.cllog.server.domain.example

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Deprecated("예시용으로, 곧 삭제될 예정입니다. (25.02.19 / kkjsw17)")
@Profile("local")
@Service
class GetExample {

    operator fun invoke(error: Boolean): String {
        if (error) {
            throw ExampleException()
        }
        return "example"
    }
}
