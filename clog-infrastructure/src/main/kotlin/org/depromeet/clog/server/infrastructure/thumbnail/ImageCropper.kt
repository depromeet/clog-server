package org.depromeet.clog.server.infrastructure.thumbnail

import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object ImageCropper {

    fun cropPngImage(file: MultipartFile): ByteArray {
        val originalImage = ImageIO.read(file.inputStream)
            ?: throw IllegalArgumentException("이미지를 읽을 수 없습니다.")

        val width = originalImage.width
        val height = originalImage.height

        val squareSize = minOf(width, height)
        val x = (width - squareSize) / 2
        val y = (height - squareSize) / 2

        val croppedImage = originalImage.getSubimage(x, y, squareSize, squareSize)

        val baos = ByteArrayOutputStream()
        ImageIO.write(croppedImage, "png", baos)
        return baos.toByteArray()
    }
}
