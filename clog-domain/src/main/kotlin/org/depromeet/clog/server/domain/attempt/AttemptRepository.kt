package org.depromeet.clog.server.domain.attempt

interface AttemptRepository {

    fun save(attempt: Attempt): Attempt
}
