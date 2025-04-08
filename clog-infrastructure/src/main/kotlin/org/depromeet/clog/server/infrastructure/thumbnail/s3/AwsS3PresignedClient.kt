package org.depromeet.clog.server.infrastructure.thumbnail.s3

import com.amazonaws.HttpMethod
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class AwsS3PresignedClient(
    @Value("\${aws.s3.bucket-name}") private val bucketName: String,
    @Value("\${aws.s3.region}") private val region: String,
    @Value("\${aws.s3.access-key}") private val accessKey: String,
    @Value("\${aws.s3.secret-key}") private val secretKey: String,
    @Value("\${aws.cloudfront.domain}") private val cloudFrontDomain: String,
) {

    private val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
        .build()

    fun generatePresignedPutUrl(objectKey: String, contentType: String): String {
        val expiration = Date(System.currentTimeMillis() + 1000 * 60 * 10)
        val request = GeneratePresignedUrlRequest(bucketName, objectKey)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration)
            .withContentType(contentType)

        return s3Client.generatePresignedUrl(request).toString()
    }

    fun getCloudFrontUrl(objectKey: String): String {
        return "https://$cloudFrontDomain/$objectKey"
    }
}
