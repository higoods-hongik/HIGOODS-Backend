package com.higoods.api.config.jwt


import com.higoods.common.annotation.Helper
import com.higoods.common.const.ACCESS_TOKEN
import com.higoods.common.const.MILLI_TO_SECOND
import com.higoods.common.const.REFRESH_TOKEN
import com.higoods.common.const.TOKEN_ISSUER
import com.higoods.common.const.TOKEN_ROLE
import com.higoods.common.const.TOKEN_TYPE
import com.higoods.common.exception.custom.ExpiredTokenException
import com.higoods.common.exception.custom.InvalidTokenException
import com.higoods.common.exception.custom.RefreshTokenExpiredException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Helper
class JwtTokenHelper(
    @Value("\${auth.jwt.secret-key}")
    private val jwtSecretKey: String,

    @Value("\${auth.jwt.access-exp}")
    val accessExpireIn: Long,

    @Value("\${auth.jwt.refresh-exp}")
    val refreshExpireIn: Long,
) {
    fun getJws(token: String): Jws<Claims> {
        return JwsParseExecuter {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        }
    }

    private val secretKey: Key
        get() = Keys.hmacShaKeyFor(jwtSecretKey.toByteArray(StandardCharsets.UTF_8))

    private fun buildAccessToken(
        id: Long,
        issuedAt: Date,
        accessTokenExpiresIn: Date,
        role: String,
    ): String {
        val encodedKey = secretKey
        return Jwts.builder()
            .setIssuer(TOKEN_ISSUER)
            .setIssuedAt(issuedAt)
            .setSubject(id.toString())
            .claim(TOKEN_TYPE, ACCESS_TOKEN)
            .claim(TOKEN_ROLE, role)
            .setExpiration(accessTokenExpiresIn)
            .signWith(encodedKey)
            .compact()
    }

    private fun buildRefreshToken(id: Long, issuedAt: Date, accessTokenExpiresIn: Date): String {
        val encodedKey = secretKey
        return Jwts.builder()
            .setIssuer(TOKEN_ISSUER)
            .setIssuedAt(issuedAt)
            .setSubject(id.toString())
            .claim(TOKEN_TYPE, REFRESH_TOKEN)
            .setExpiration(accessTokenExpiresIn)
            .signWith(encodedKey)
            .compact()
    }

    fun generateAccessToken(id: Long, role: String): String {
        val issuedAt = Date()
        val accessTokenExpiresIn = Date(issuedAt.time + accessExpireIn * MILLI_TO_SECOND)
        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role)
    }

    fun generateRefreshToken(id: Long): String {
        val issuedAt = Date()
        val refreshTokenExpiresIn = Date(issuedAt.time + refreshExpireIn * MILLI_TO_SECOND)
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn)
    }

    fun isAccessToken(token: String): Boolean {
        return getJws(token).body[TOKEN_TYPE] == ACCESS_TOKEN
    }

    fun isRefreshToken(token: String): Boolean {
        return getJws(token).body[TOKEN_TYPE] == REFRESH_TOKEN
    }

    fun parseAccessToken(token: String): AccessTokenInfo {
        if (isAccessToken(token)) {
            val claims: Claims = getJws(token).body
            return AccessTokenInfo(claims.subject.toLong(), claims[TOKEN_ROLE] as String)
        }
        throw InvalidTokenException.EXCEPTION
    }

    fun parseRefreshToken(token: String): Long {
        try {
            if (isRefreshToken(token)) {
                val claims: Claims = getJws(token).body
                return claims.subject.toLong()
            }
        } catch (e: ExpiredTokenException) {
            throw RefreshTokenExpiredException.EXCEPTION
        }
        throw InvalidTokenException.EXCEPTION
    }
}
