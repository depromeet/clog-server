package org.depromeet.clog.server.api.user.application

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.domain.exception.UserNotFoundException
import org.hibernate.validator.constraints.Length
import org.springframework.stereotype.Service

@Service
class UpdateUser(
    private val userRepository: UserRepository,
) {

    operator fun invoke(
        userId: Long,
        command: Command,
    ): Result {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()

        val name = command.name?.trim()
            ?.takeIf { it.isNotBlank() }

        user.copy(
            name = name ?: user.name,
            height = command.height ?: user.height,
            armSpan = command.armSpan ?: user.armSpan,
            instagramUrl = command.instagramUrl ?: user.instagramUrl,
        ).let { updatedUser ->
            userRepository.save(updatedUser)
        }

        return Result(userId = user.id!!)
    }

    data class Command(
        @Length(min = 1, max = 10)
        @Schema(title = "이름", example = "권기준")
        val name: String? = null,

        @field:Min(1)
        @field:Max(999)
        @Schema(title = "키", description = "1 ~ 999 사이의 값", example = "180.0")
        val height: Double? = null,

        @field:Min(1)
        @field:Max(999)
        @Schema(title = "팔 길이", description = "1 ~ 999 사이의 값", example = "190.0")
        val armSpan: Double? = null,

        @field:Pattern(
            regexp = "^https://(www\\.)?instagram\\.com/[A-Za-z0-9._]{1,30}/?$",
            message = "유효한 인스타그램 프로필 URL을 입력하세요.",
        )
        @Schema(title = "인스타그램 URL", example = "https://www.instagram.com/kkjsw17/")
        val instagramUrl: String? = null,
    )

    data class Result(
        val userId: Long,
    )
}
