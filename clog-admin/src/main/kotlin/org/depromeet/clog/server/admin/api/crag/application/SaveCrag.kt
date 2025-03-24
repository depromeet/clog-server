package org.depromeet.clog.server.admin.api.crag.application

import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragDto
import org.depromeet.clog.server.domain.admin.CragAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.Location
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveCrag(
    private val cragAdminRepository: CragAdminRepository
) {

    @Transactional
    operator fun invoke(
        request: SaveCragDto.Request
    ) {
        request.withDefaults()
        val location = Location(
            longitude = request.longitude!!.toDouble(),
            latitude = request.latitude!!.toDouble()
        )

        val crag = Crag(
            null,
            request.name!!,
            request.roadAddress!!,
            location,
            request.kakaoPlaceId!!.toLong()
        )

        cragAdminRepository.save(crag)
    }
}
