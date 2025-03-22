package org.depromeet.clog.server.infrastructure.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.depromeet.clog.server.domain.report.DailyReportStatistic
import org.depromeet.clog.server.domain.video.VideoQuery
import org.depromeet.clog.server.infrastructure.report.DailyReportStatisticEntity
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DailyReportStatisticMapper {
    private val objectMapper = jacksonObjectMapper()
    private val logger = KotlinLogging.logger {}

    fun toDomain(entity: DailyReportStatisticEntity): DailyReportStatistic {
        val videos: List<VideoQuery> = try {
            objectMapper.readValue(entity.attemptVideosJson)
        } catch (e: Exception) {
            logger.error { "JSON 파싱 중 오류 발생: ${e.message}" }
            emptyList()
        }
        return DailyReportStatistic(
            userId = entity.userId,
            mostAttemptedProblemCrag = entity.mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = entity.mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = entity.mostAttemptedProblemAttemptCount,
            attemptVideos = videos,
            mostVisitedCragName = entity.mostVisitedCragName,
            mostVisitedCragVisitCount = entity.mostVisitedCragVisitCount
        )
    }

    fun toEntity(domain: DailyReportStatistic): DailyReportStatisticEntity {
        return DailyReportStatisticEntity(
            id = null,
            userId = domain.userId,
            statDate = LocalDate.now(),
            mostAttemptedProblemCrag = domain.mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = domain.mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = domain.mostAttemptedProblemAttemptCount,
            attemptVideosJson = writeAttemptVideos(domain.attemptVideos),
            mostVisitedCragName = domain.mostVisitedCragName,
            mostVisitedCragVisitCount = domain.mostVisitedCragVisitCount
        )
    }

    fun writeAttemptVideos(videos: List<VideoQuery>): String {
        return objectMapper.writeValueAsString(videos)
    }
}
