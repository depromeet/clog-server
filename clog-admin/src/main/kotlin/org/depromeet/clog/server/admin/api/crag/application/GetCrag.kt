package org.depromeet.clog.server.admin.api.crag.application

import jakarta.persistence.EntityNotFoundException
import org.depromeet.clog.server.admin.api.crag.presentation.dto.CragResult
import org.depromeet.clog.server.domain.admin.CragAdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCrag(
    private val cragAdminRepository: CragAdminRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        id: Long
    ): CragResult {
        val crag = cragAdminRepository.findById(id)
            ?: throw EntityNotFoundException("Crag with ID $id not found")

        return CragResult.from(crag)
    }
}
