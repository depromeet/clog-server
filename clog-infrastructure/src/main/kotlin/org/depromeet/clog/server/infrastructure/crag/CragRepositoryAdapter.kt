package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CragRepositoryAdapter(
    private val cragJpaRepository: CragJpaRepository
) : CragRepository {

    override fun save(crag: Crag): Crag =
        cragJpaRepository.save(CragEntity.fromDomain(crag)).toDomain()

    override fun saveAll(crags: List<Crag>): List<Crag> =
        cragJpaRepository.saveAll(ArrayList(crags.map { CragEntity.fromDomain(it) }))
            .toList()
            .map { it.toDomain() }

    override fun existsByKakaoPlaceId(kakaoPlaceId: Long): Boolean =
        cragJpaRepository.existsByKakaoPlaceId(kakaoPlaceId)

    override fun findById(id: Long): Crag? =
        cragJpaRepository.findByIdOrNull(id)?.toDomain()
}
