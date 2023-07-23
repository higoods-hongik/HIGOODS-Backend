package com.higoods.api.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.higoods.common.const.SWAGGER_PATTERNS
import com.higoods.common.exception.BaseErrorCode
import com.higoods.common.exception.GlobalErrorCode
import com.higoods.common.exception.HiGoodsCodeException
import com.higoods.common.exception.dto.ErrorResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.util.PatternMatchUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccessDeniedFilter(
    val objectMapper: ObjectMapper
) :
    OncePerRequestFilter() {
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val servletPath = request.servletPath
        return PatternMatchUtils.simpleMatch(SWAGGER_PATTERNS, servletPath)
    }

    @Throws(ServletException::class, IOException::class)
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
        } catch (e: AccessDeniedException) {
            val accessDenied = ErrorResponse(
                GlobalErrorCode.ACCESS_TOKEN_NOT_EXIST.errorReason.status,
                GlobalErrorCode.ACCESS_TOKEN_NOT_EXIST.errorReason.code,
                GlobalErrorCode.ACCESS_TOKEN_NOT_EXIST.errorReason.reason,
                request.requestURL.toString()
            )
            responseToClient(response, accessDenied)
        }
    }

    private fun getErrorResponse(errorCode: BaseErrorCode, path: String): ErrorResponse {
        val (status, code, reason) = errorCode.errorReason
        return ErrorResponse(
            status,
            code,
            reason,
            path
        )
    }

    @Throws(IOException::class)
    private fun responseToClient(response: HttpServletResponse, errorResponse: ErrorResponse) {
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = errorResponse.status
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
