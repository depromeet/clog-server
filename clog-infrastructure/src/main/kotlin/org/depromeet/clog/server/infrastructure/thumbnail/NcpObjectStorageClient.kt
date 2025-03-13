package org.depromeet.clog.server.infrastructure.thumbnail

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class NcpObjectStorageClient(
    @Value("\${ncp.storage.bucket-name}") private val bucketName: String,
    @Value("\${ncp.storage.region}") private val region: String,
    @Value("\${ncp.storage.access-key}") private val accessKey: String,
    @Value("\${ncp.storage.secret-key}") private val secretKey: String,
    @Value("\${ncp.storage.end-point}") private val endpoint: String
) {

    private val s3Client: AmazonS3 = run {
        val credentials = BasicAWSCredentials(accessKey, secretKey)
        AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, region))
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withPathStyleAccessEnabled(true)
            .build()
    }

    fun uploadFile(file: MultipartFile): String {
        val fileName = generateUniqueFileName(file.originalFilename)
        val metadata = ObjectMetadata().apply {
            contentLength = file.size
            contentType = file.contentType
        }
        s3Client.putObject(bucketName, fileName, file.inputStream, metadata)
        s3Client.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead) // ✅ 추가됨

        return s3Client.getUrl(bucketName, fileName).toString()
    }

    private fun generateUniqueFileName(originalFilename: String?): String {
        return if (!originalFilename.isNullOrBlank()) {
            "${UUID.randomUUID()}-$originalFilename"
        } else {
            UUID.randomUUID().toString()
        }
    }
}
