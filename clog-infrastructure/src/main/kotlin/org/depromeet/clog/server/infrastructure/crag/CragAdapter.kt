package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.crag.domain.Location
import org.depromeet.clog.server.infrastructure.mappers.CragMapper
import org.depromeet.clog.server.infrastructure.mappers.LocationMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CragAdapter(
    private val cragMapper: CragMapper,
    private val locationMapper: LocationMapper,
    private val cragJpaRepository: CragJpaRepository
) : CragRepository {

    override fun save(crag: Crag): Crag {
        val entity = cragJpaRepository.save(cragMapper.toEntity(crag))
        return cragMapper.toDomain(entity)
    }

    override fun saveAll(crags: List<Crag>): List<Crag> =
        cragJpaRepository.saveAll(crags.map { cragMapper.toEntity(it) })
            .map { cragMapper.toDomain(it) }

    override fun existsByKakaoPlaceId(kakaoPlaceId: Long): Boolean =
        cragJpaRepository.existsByKakaoPlaceId(kakaoPlaceId)

    override fun findDistinctCragsByUserId(userId: Long, cursor: Long?, pageSize: Int): List<Crag> {
        val pageable = PageRequest.of(0, pageSize)
        return cragJpaRepository.findDistinctCragsByUserIdWithCursor(userId, cursor, pageable)
            .map { cragMapper.toDomain(it) }
    }

    override fun findNearCragsByLocation(location: Location, cursor: Double?, pageSize: Int): List<Pair<Crag, Double>> {
        val userPoint = locationMapper.toPoint(location)

        val crags = cragJpaRepository.findAll(limit = pageSize + 1) {
            val distanceExpression = expression<Double>(
                "ST_Distance" +
                    "(" +
                    "location, " +
                    "ST_GeomFromText('POINT(${userPoint.coordinate.x} ${userPoint.coordinate.y})'" +
                    ", 5181)" +
                    ")"
            )

            select(
                entity(CragEntity::class),
            )
                .from(
                    entity(CragEntity::class)
                )
                .where(
                    cursor?.let {
                        distanceExpression.greaterThanOrEqualTo(cursor)
                    }
                )
                .orderBy(distanceExpression.asc())
        }

        return crags.filterNotNull().map { cragEntity ->
            val distance = userPoint.distance(cragEntity.location)
            cragMapper.toDomain(cragEntity) to distance
        }
    }

    override fun findById(id: Long): Crag? =
        cragJpaRepository.findByIdOrNull(id)?.let {
            cragMapper.toDomain(it)
        }
}
