package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.admin.domain.crag.CragAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.depromeet.clog.server.infrastructure.crag.CragJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CragAdminAdapter(
    private val cragJpaRepository: CragJpaRepository
) : CragAdminRepository {

    override fun save(crag: Crag): Crag {
        return cragJpaRepository.save(CragEntity.fromDomain(crag)).toDomain()
    }

    override fun findById(id: Long): Crag? {
        return cragJpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findAll(): List<Crag> {
        return cragJpaRepository.findAll().map { it.toDomain() }
    }
}
