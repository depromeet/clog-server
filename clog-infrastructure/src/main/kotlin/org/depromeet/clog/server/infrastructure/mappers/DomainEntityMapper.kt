package org.depromeet.clog.server.infrastructure.mappers

interface DomainEntityMapper<QD, CD, E> {
    fun toDomain(entity: E): QD
    fun toEntity(domain: CD): E
}
