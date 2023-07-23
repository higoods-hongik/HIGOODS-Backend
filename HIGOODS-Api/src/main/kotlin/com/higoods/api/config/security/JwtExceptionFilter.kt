package com.higoods.api.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.HiGoodsCodeException
import com.higoods.common.exception.dto.ErrorResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtExceptionFilter(
    val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: HiGoodsCodeException) {
            responseToClient(
                response,
                getErrorResponse(e.errorCode, request.requestURL.toString())
            )
        }
    }

    private fun getErrorResponse(errorCode: BaseErrorCode, path: String): ErrorResponse {
        return ErrorResponse.of(errorCode.errorReason, path)
    }

    private fun responseToClient(response: HttpServletResponse, errorResponse: ErrorResponse) {
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = errorResponse.status
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
