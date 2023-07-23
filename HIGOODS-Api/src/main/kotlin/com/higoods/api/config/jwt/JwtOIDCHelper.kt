package com.higoods.api.config.jwt

import com.depromeet.whatnow.config.jwt.OIDCDecodePayload
import com.higoods.common.annotation.Helper
import com.higoods.common.const.KID
import com.higoods.common.exception.custom.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import java.math.BigInteger
import java.security.Key
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Helper
class JwtOIDCHelper {

    fun getKidFromUnsignedTokenHeader(token: String, iss: String, aud: String): String {
        return getUnsignedTokenClaims(token, iss, aud).header[KID] as String
    }

    private fun getUnsignedTokenClaims(token: String, iss: String, aud: String): Jwt<Header<*>, Claims> {
        return JwtParseExecuter {
            Jwts.parserBuilder()
                .requireAudience(aud)
                .requireIssuer(iss)
                .build()
                .parseClaimsJwt(getUnsignedToken(token))
        }
    }

    private fun getUnsignedToken(token: String): String {
        val splitToken = token.split("\\.".toRegex())
        if (splitToken.size != 3) throw InvalidTokenException.EXCEPTION
        return splitToken[0] + "." + splitToken[1] + "."
    }

    fun getOIDCTokenJws(token: String, modulus: String, exponent: String): Jws<Claims> {
        return JwsParseExecuter {
            Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus, exponent))
                .build()
                .parseClaimsJws(token)
        }
    }

    fun getOIDCTokenBody(token: String, modulus: String, exponent: String): OIDCDecodePayload {
        val body = getOIDCTokenJws(token, modulus, exponent).body
        return OIDCDecodePayload(
            body.issuer,
            body.audience,
            body.subject
        )
    }

    private fun getRSAPublicKey(modulus: String, exponent: String): Key {
        val keyFactory = KeyFactory.getInstance("RSA")
        val decodeN = Base64.getUrlDecoder().decode(modulus)
        val decodeE = Base64.getUrlDecoder().decode(exponent)
        val n = BigInteger(1, decodeN)
        val e = BigInteger(1, decodeE)
        val keySpec = RSAPublicKeySpec(n, e)
        return keyFactory.generatePublic(keySpec)
    }
}
