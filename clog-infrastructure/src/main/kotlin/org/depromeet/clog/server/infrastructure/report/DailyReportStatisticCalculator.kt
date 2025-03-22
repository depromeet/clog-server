package org.depromeet.clog.server.infrastructure.report

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.depromeet.clog.server.domain.report.DailyReportStatistic
import org.depromeet.clog.server.domain.video.VideoQuery
import org.depromeet.clog.server.infrastructure.mappers.VideoMapper
import org.depromeet.clog.server.infrastructure.video.VideoEntity
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DailyReportStatisticCalculator(
    @PersistenceContext private val em: EntityManager,
    private val videoMapper: VideoMapper
) {
    private val logger = KotlinLogging.logger {}

    private data class GroupData(
        val groupDate: LocalDate,
        val cragId: Long,
        val problemId: Long,
        val attemptCount: Long
    )

    fun computeForUser(userId: Long): DailyReportStatistic {
        val groupData = getGroupData(userId)
            ?: return DailyReportStatistic(
                userId = userId,
                mostAttemptedProblemCrag = "없음",
                mostAttemptedProblemGrade = "없음",
                mostAttemptedProblemAttemptCount = 0,
                attemptVideos = emptyList(),
                mostVisitedCragName = "없음",
                mostVisitedCragVisitCount = 0
            )

        val details: ReportDTO = getDetails(userId, groupData.groupDate, groupData.problemId)
        val story = details.story
        val problem = details.problem

        val mostAttemptedProblemCrag = story.crag?.name ?: "알 수 없음"
        val mostAttemptedProblemGrade = problem.grade?.color?.name ?: "알 수 없음"

        val attemptVideos =
            getAttemptVideos(userId, groupData.groupDate, groupData.problemId, groupData.cragId)
        val (mostVisitedCragName, mostVisitedCragVisitCount) = getVisitedData(userId)

        return DailyReportStatistic(
            userId = userId,
            mostAttemptedProblemCrag = mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = groupData.attemptCount,
            attemptVideos = attemptVideos,
            mostVisitedCragName = mostVisitedCragName,
            mostVisitedCragVisitCount = mostVisitedCragVisitCount
        ).also {
            logger.info { "사용자 id $userId 에 대한 통계가 계산되었습니다: $it" }
        }
    }

    private fun getGroupData(userId: Long): GroupData? {
        val queryStr = """
            SELECT s.date, s.crag.id, p.id, COUNT(a.id)
            FROM AttemptEntity a
            JOIN a.problem p
            JOIN p.story s
            WHERE s.userId = :userId
            GROUP BY s.date, s.crag.id, p.id
            ORDER BY COUNT(a.id) DESC
        """
        val result = em.createQuery(queryStr)
            .setParameter("userId", userId)
            .setMaxResults(1)
            .resultList
        return if (result.isEmpty()) {
            null
        } else {
            val row = result[0] as Array<Any>
            GroupData(
                groupDate = row[0] as LocalDate,
                cragId = row[1] as Long,
                problemId = row[2] as Long,
                attemptCount = row[3] as Long
            )
        }
    }

    private fun getDetails(userId: Long, groupDate: LocalDate, problemId: Long): ReportDTO {
        val queryStr = """
        SELECT new org.depromeet.clog.server.infrastructure.report.ReportDTO(s, p)
        FROM ProblemEntity p
        JOIN p.story s
        WHERE s.userId = :userId 
          AND s.date = :groupDate 
          AND p.id = :problemId
    """
        return em.createQuery(queryStr, ReportDTO::class.java)
            .setParameter("userId", userId)
            .setParameter("groupDate", groupDate)
            .setParameter("problemId", problemId)
            .singleResult
    }

    private fun getAttemptVideos(
        userId: Long,
        groupDate: LocalDate,
        problemId: Long,
        cragId: Long
    ): List<VideoQuery> {
        val queryStr = """
            SELECT v
            FROM AttemptEntity a
            JOIN a.video v
            JOIN a.problem p
            JOIN p.story s
            WHERE s.userId = :userId 
              AND s.date = :groupDate 
              AND p.id = :problemId 
              AND s.crag.id = :cragId
            ORDER BY v.id DESC
        """
        val videosList = em.createQuery(queryStr, VideoEntity::class.java)
            .setParameter("userId", userId)
            .setParameter("groupDate", groupDate)
            .setParameter("problemId", problemId)
            .setParameter("cragId", cragId)
            .resultList
        return videosList.map { video -> videoMapper.toDomainWithoutStamps(video) }
    }

    private fun getVisitedData(userId: Long): Pair<String, Long> {
        val queryStr = """
            SELECT s.crag.name, COUNT(DISTINCT s.date)
            FROM StoryEntity s
            WHERE s.userId = :userId
            GROUP BY s.crag.name
            ORDER BY COUNT(DISTINCT s.date) DESC
        """
        val result = em.createQuery(queryStr)
            .setParameter("userId", userId)
            .setMaxResults(1)
            .resultList
        if (result.isEmpty()) {
            throw IllegalStateException("사용자 id: $userId 에 대한 방문 암장 데이터를 찾을 수 없습니다.")
        }
        val row = result[0] as Array<Any>
        val cragName = row[0] as String
        val visitCount = row[1] as Long
        return Pair(cragName, visitCount)
    }
}
