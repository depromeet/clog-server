package org.depromeet.clog.server.domain.video

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class VideoNotFoundException(
    message: String = "존재하지 않는 영상입니다.",
) : ClogException(
    errorCode = ErrorCode.VIDEO_NOT_FOUND,
    message = message,
)
