package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.springframework.stereotype.Repository

@Repository
class CragRepositoryAdapter(
    private val cragJpaRepository: CragJpaRepository
) : CragRepository {

    override fun save(crag: Crag): Crag =
        cragJpaRepository.save(CragEntity.fromDomain(crag)).toDomain()
}
