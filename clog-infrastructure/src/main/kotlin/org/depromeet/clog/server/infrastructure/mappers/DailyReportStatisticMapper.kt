package org.depromeet.clog.server.infrastructure.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.depromeet.clog.server.domain.report.DailyReportStatistic
import org.depromeet.clog.server.domain.video.VideoQuery
import org.depromeet.clog.server.infrastructure.report.DailyReportStatisticEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DailyReportStatisticMapper {
    private val objectMapper = jacksonObjectMapper()
    private val logger = LoggerFactory.getLogger(DailyReportStatisticMapper::class.java)

    fun toDomain(entity: DailyReportStatisticEntity): DailyReportStatistic {
        val videos: List<VideoQuery> = try {
            objectMapper.readValue(entity.attemptVideosJson)
        } catch (e: Exception) {
            logger.error("JSON 파싱 중 오류 발생: ${e.message}", e)
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
            userId = domain.userId,
            mostAttemptedProblemCrag = domain.mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = domain.mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = domain.mostAttemptedProblemAttemptCount,
            attemptVideosJson = objectMapper.writeValueAsString(domain.attemptVideos),
            mostVisitedCragName = domain.mostVisitedCragName,
            mostVisitedCragVisitCount = domain.mostVisitedCragVisitCount
        )
    }
}
