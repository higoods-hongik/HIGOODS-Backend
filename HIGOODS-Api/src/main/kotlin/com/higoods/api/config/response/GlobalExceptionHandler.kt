package com.higoods.api.config.response

import com.fasterxml.jackson.databind.ObjectMapper
import com.higoods.api.config.security.SecurityUtils
import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException
import com.higoods.common.exception.HiGoodsDynamicException
import com.higoods.common.exception.dto.ErrorReason
import com.higoods.common.exception.dto.ErrorResponse
import com.higoods.infra.api.slack.SlackAsyncErrorSender
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler(
    var slackAsyncErrorSender: SlackAsyncErrorSender,
    val objectMapper: ObjectMapper
) : ResponseEntityExceptionHandler() {

    /**요청 url을 resource로 담아 상위 객체에 처리를 위임한다.*/
    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("Exception", ex)
        val servletWebRequest = request as ServletWebRequest
        val url = UriComponentsBuilder.fromHttpRequest(
            ServletServerHttpRequest(servletWebRequest.request)
        )
            .build()
            .toUriString()

        val errorResponse = ErrorResponse(
            status.value(),
            status.name,
            ex.message,
            url
        )
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("MethodArgumentNotValidException Exception", ex)
        val url = UriComponentsBuilder.fromHttpRequest(
            ServletServerHttpRequest((request as ServletWebRequest).request)
        )
            .build()
            .toUriString()
        val fieldErrorMessage = ex.bindingResult.fieldErrors
            .map { fieldError -> fieldError.field + " " + fieldError.defaultMessage }

        val errorToJsonString = objectMapper.writeValueAsString(fieldErrorMessage)

        val errorResponse = ErrorResponse(
            status = status.value(),
            code = status.name,
            reason = errorToJsonString,
            path = url
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /** Request Param Validation 예외 처리*/
//    @ExceptionHandler(Constra::class)
//    fun constraintViolationExceptionHandler(
//        e: Constra,
//        request: HttpServletRequest,
//    ): ResponseEntity<ErrorResponse?>? {
//        logger.error("ConstraintViolationException Exception", e)
//
//        val bindingErrors: MutableMap<String?, Any> = HashMap()
//        e.constraintViolations.forEach {
//                constraintViolation ->
//            val propertyPath = constraintViolation.propertyPath.toString().split(".").dropLastWhile { it.isEmpty() }
//            val path = propertyPath.takeLast(1).firstOrNull()
//            bindingErrors[path] = constraintViolation.message
//        }
//        val errorReason = ErrorReason.of(
//            status = BAD_REQUEST,
//            code = bindingErrors.toString(),
//            reason = bindingErrors.toString(),
//        )
//        val errorResponse = ErrorResponse.of(errorReason, request.requestURL.toString())
//        return ResponseEntity.status(errorReason.status)
//            .body(errorResponse)
//    }

    @ExceptionHandler(HiGoodsDynamicException::class)
    fun hiGoodsDynamicExceptionHandler(
        e: HiGoodsDynamicException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse?>? {
        logger.error("WhatnowDynamicException Exception", e)
        val errorResponse = ErrorResponse(
            e.status,
            e.code,
            e.reason,
            path = request.requestURL.toString()
        )
        return ResponseEntity.status(HttpStatus.valueOf(e.status)).body<ErrorResponse>(errorResponse)
    }

    @ExceptionHandler(HiGoodsCodeException::class)
    fun codeExceptionHandler(
        e: HiGoodsCodeException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse?>? {
        logger.error("WhatnowCodeException Exception", e)
        val errorReason: ErrorReason = e.errorReason
        val errorResponse = ErrorResponse.of(errorReason, request.requestURL.toString())
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.status))
            .body<ErrorResponse>(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    private fun handleException(
        e: Exception,
        request: HttpServletRequest?
    ): ResponseEntity<ErrorResponse?> {
        logger.error("Exception", e)

        val cachingRequest = request as ContentCachingRequestWrapper
        val userId: Long = SecurityUtils.currentUserId
        val url = UriComponentsBuilder.fromHttpRequest(ServletServerHttpRequest(request))
            .build()
            .toUriString()

        val internalServerError = GlobalErrorCode.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(
            internalServerError.status,
            internalServerError.code,
            internalServerError.reason,
            url
        )
        slackAsyncErrorSender.execute(cachingRequest, e, userId)
        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.status))
            .body<ErrorResponse>(errorResponse)
    }
}
