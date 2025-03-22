package org.depromeet.clog.server.infrastructure.report

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.depromeet.clog.server.domain.video.VideoQuery

@Converter(autoApply = false)
class VideoQueryListConverter(private val objectMapper: ObjectMapper) :
    AttributeConverter<List<VideoQuery>, String> {
    override fun convertToDatabaseColumn(attribute: List<VideoQuery>?): String {
        return attribute?.let { objectMapper.writeValueAsString(it) } ?: "[]"
    }

    override fun convertToEntityAttribute(dbData: String?): List<VideoQuery> {
        return if (dbData.isNullOrEmpty()) emptyList() else objectMapper.readValue(dbData)
    }
}
