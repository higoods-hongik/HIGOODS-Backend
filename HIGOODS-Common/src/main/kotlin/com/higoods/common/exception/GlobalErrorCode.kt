package com.higoods.common.exception

import com.higoods.common.const.BAD_REQUEST
import com.higoods.common.const.FORBIDDEN
import com.higoods.common.const.INTERNAL_SERVER
import com.higoods.common.const.METHOD_NOT_ALLOWED
import com.higoods.common.const.NOT_FOUND
import com.higoods.common.const.REQUEST_TIMEOUT
import com.higoods.common.const.SERVICE_UNAVAILABLE
import com.higoods.common.const.TOO_MANY_REQUESTS
import com.higoods.common.const.UNAUTHORIZED
import com.higoods.common.exception.dto.ErrorReason
import java.util.*

enum class GlobalErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {
    @ExplainError("예상할 수 없는 에러가 발생했습니다. 관리자에게 문의하세요")
    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "GLOBAL_500_1", "서버 오류, 관리자에게 문의하세요"),

    @ExplainError("밸리데이션 (검증 과정 수행속 ) 발생하는 오류입니다.")
    ARGUMENT_NOT_VALID_ERROR(BAD_REQUEST, "GLOBAL_400_1", "잘못된 요청입니다."),

    @ExplainError("accessToken 만료시 발생하는 오류입니다.")
    TOKEN_EXPIRED(UNAUTHORIZED, "AUTH_401_1", "토큰이 만료되었습니다."),

    @ExplainError("refresgToken 만료시 발생하는 오류입니다.")
    REFRESH_TOKEN_EXPIRED(FORBIDDEN, "AUTH_403_1", "인증시간이 만료되었습니다. 인증토큰 재발급을 요청하세요"),

    @ExplainError("헤더에 accessToken의 형식이 틀릴떄 발생하는 오류입니다, 토큰이 위조됐을 가능성이 있습니다.")
    INVALID_TOKEN(FORBIDDEN, "AUTH_403_2", "토큰의 형식이 일치하지 않습니다. 적절한 형식으로 다시 요청하세요"),

    @ExplainError("헤더에 accessToken이 없을 때 발생하는 오류입니다.")
    ACCESS_TOKEN_NOT_EXIST(UNAUTHORIZED, "AUTH_401_2", "토큰이 존재하지 않습니다. 적절한 토큰을 헤더에 넣어주세요"),

    @ExplainError("헤더에 accessToken이 없을때 발생하는 오류입니다")
    ACCESS_TOKEN_NOT_FOUND(FORBIDDEN, "AUTH_403_3", "토큰이 존재하지 않습니다. 적절한 토큰을 헤더에 넣어주세요"),

    @ExplainError("없는 resource로 요청했습니다. 다른 요청으로 다시 시도해주세요")
    RESOURCE_NOT_FOUND(NOT_FOUND, "GLOBAL_404_1", "The requested resource was not found on the server."),

    @ExplainError("요청시간이 초과되었습니다.")
    RESOURCE_REQUEST_TIMEOUT(REQUEST_TIMEOUT, "GLOBAL_408_1", "요청"),

    @ExplainError("많은 요청을 보냈을 때 발생하는 오류입니다.")
    SERVER_TOO_MANY_REQUESTS(TOO_MANY_REQUESTS, "GLOBAL_429_1", "많은 요청을 보냈습니다. 잠시 후 다시 시도해주세요"),

    @ExplainError("잘못된 락 식별자를 사용했을 때 발생하는 오류입니다.")
    BAD_LOCK_IDENTIFIER(INTERNAL_SERVER, "GLOBAL_500_2", "잘못된 락 식별자입니다."),

    @ExplainError("요청한 파일 확장자가 허용되지 않을 때 발생하는 오류입니다.")
    BAD_FILE_EXTENSION(INTERNAL_SERVER, "GLOBAL_500_3", "잘못된 파일 확장자입니다."),

    @ExplainError("요청한 메소드가 허용되지 않을 떄 발생하는 오류입니다.")
    SERVER_METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "GLOBAL_405_1", "요청한 메소드가 허용되지 않습니다."),

    @ExplainError("서버과 현재 유지보수이거나 과부하로 이용할 수 없을 때 발생하는 오류입니다.")
    SERVER_UNAVAILABLE(SERVICE_UNAVAILABLE, "GLOBAL_503_1", "현재 유지보수 및 과부하로 서버를 이용할 수 없습니다. 잠시 후 다시 시도해주세요"),

    @ExplainError("레디스 락을 사용할 수 없을 때 발생하는 오류입니다.")
    NOT_AVAILABLE_REDISSON_LOCK(INTERNAL_SERVER, "GLOBAL_500_4", "레디스 락을 사용할 수 없습니다. 관리자에게 문의하세요"),

    @ExplainError("다른 서버에서 발생한 예외입니다.")
    OTHER_SERVER_BAD_REQUEST(BAD_REQUEST, "OTHER_SERVER_400_1", "다른 서버에서 잘못된 요청을 보냈습니다."),

    @ExplainError("다른 서버에서 발생한 인증 예외입니다.")
    OTHER_SERVER_UNAUTHORIZED(BAD_REQUEST, "OTHER_SERVER_401_1", "다른 서버에서 인증되지 않은 요청을 보냈습니다."),

    @ExplainError("다른 서버에서 발생한 인증 예외입니다.")
    OTHER_SERVER_FORBIDDEN(BAD_REQUEST, "OTHER_SERVER_403_1", "다른 서버에서 인증되지 않은 요청을 보냈습니다."),

    @ExplainError("다른 서버에서 발생한 토큰 관련 인증 예외입니다")
    OTHER_SERVER_EXPIRED_TOKEN(UNAUTHORIZED, "OTHER_SERVER_403_2", "다른 서버에서 만료된 토큰으로 인증되지 않은 요청을 보냈습니다."),

    @ExplainError("다른 서버에서 잘못된 Resource를 요청했을 때 발생하는 예외입니다.")
    OTHER_SERVER_NOT_FOUND(NOT_FOUND, "OTHER_SERVER_404_1", "다른 서버에서 잘못된 Resource를 요청했습니다."),

    @ExplainError("다른 서버에서 발생한 알 수 없는 서버 예외입니다.")
    OTHER_SERVER_INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "OTHER_SERVER_500_1", "다른 서버에서 알 수 없는 서버 오류가 발생했습니다."),

    SECURITY_CONTEXT_NOT_FOUND(INTERNAL_SERVER, "GLOBAL_500_2", "security context not found"),

    @ExplainError("해당 약속에 유저가 참여하지 않을때 발생하는 오류입니다.")
    USER_NOT_PARTICIPATE(BAD_REQUEST, "GLOBAL_400_2", "해당 약속에 유저가 참여하지 않습니다."),

    @ExplainError("userId를 Long으로 변환시 발생하는 오류입니다.")
    USER_ID_NOT_LONG(INTERNAL_SERVER, "GLOBAL_500_5", "userId를 Long으로 변환시 발생하는 오류입니다."),

    @ExplainError("userId가 파라미터에 없을 때 발생하는 오류입니다.")
    USER_ID_PARAMETER_NOT_FOUND(BAD_REQUEST, "GLOBAL_400_3", "userId가 파라미터에 없습니다.")

    ;

    override val errorReason: ErrorReason
        get() { return ErrorReason(status, code, reason) }

    override val explainError: String
        get() {
            val field = this.javaClass.getField(name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return if (Objects.nonNull(annotation)) annotation.value else reason
        }
}
