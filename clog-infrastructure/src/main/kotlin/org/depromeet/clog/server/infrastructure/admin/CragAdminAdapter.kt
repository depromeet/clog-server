package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.admin.domain.crag.CragAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.infrastructure.crag.CragJpaRepository
import org.depromeet.clog.server.infrastructure.mappers.CragMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CragAdminAdapter(
    private val cragMapper: CragMapper,
    private val cragJpaRepository: CragJpaRepository
) : CragAdminRepository {

    override fun save(crag: Crag): Crag {
        val entity = cragJpaRepository.save(cragMapper.toEntity(crag))
        return cragMapper.toDomain(entity)
    }

    override fun findById(id: Long): Crag? {
        val entity = cragJpaRepository.findByIdOrNull(id)
        return cragMapper.toDomain(entity!!)
    }

    override fun findAll(): List<Crag> {
        return cragJpaRepository.findAll().map { cragMapper.toDomain(it) }
    }
}
