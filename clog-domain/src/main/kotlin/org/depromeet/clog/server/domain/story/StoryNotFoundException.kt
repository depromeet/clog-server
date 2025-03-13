package org.depromeet.clog.server.domain.story

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class StoryNotFoundException(
    message: String = "스토리를 찾을 수 없습니다.",
) : ClogException(
    errorCode = ErrorCode.STORY_NOT_FOUND,
    message = message,
)
