package org.depromeet.clog.server.api.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.common.ErrorResponse
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class SwaggerConfiguration(
    @Value("\${spring.application.name}") private val appName: String,
    @Value("\${spring.application.version}") private val appVersion: String
) {

    @Bean
    fun openAPI(): OpenAPI {
        val jwt = "JWT"
        val securityRequirement: SecurityRequirement = SecurityRequirement().addList(jwt)
        val components: Components = Components().addSecuritySchemes(
            jwt,
            SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        )
        return OpenAPI()
            .components(Components())
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
            .components(components)
    }

    @Bean
    fun errorResponseCustomizer(): OperationCustomizer = OperationCustomizer { operation, handlerMethod ->
        handlerMethod.getMethodAnnotation(ApiErrorCodes::class.java)
            ?.let { addErrorExamples(operation, it.value) }

        operation
    }

    private fun addErrorExamples(operation: Operation, codes: Array<ErrorCode>) {
        val detail = mapOf("detail1" to "detail1", "detail2" to "detail2")
        val examplesByStatus = codes
            .map { code ->
                val example = Example().apply { value = ErrorResponse.from(code, detail) }
                Triple(code.httpStatus, code.name, example)
            }
            .groupBy({ it.first }, { Pair(it.second, it.third) })

        addExamplesToResponses(operation.responses, examplesByStatus)
    }

    private fun addExamplesToResponses(
        responses: ApiResponses,
        examplesByStatus: Map<Int, List<Pair<String, Example>>>
    ) {
        examplesByStatus.forEach { (status, examples) ->
            val mediaType = MediaType().apply {
                examples.forEach { (name, example) ->
                    addExamples(name, example)
                }
            }
            val ct = Content().apply { addMediaType("application/json", mediaType) }
            val apiResponse = ApiResponse().apply { content = ct }
            responses.addApiResponse(status.toString(), apiResponse)
        }
    }

    private fun apiInfo(): Info {
        return Info()
            .title(appName.uppercase(Locale.getDefault()).replace("-", " "))
            .description("세계 최강의 클라이밍 기록 서비스 CLOG API 문서입니다.")
            .version(appVersion)
    }
}
