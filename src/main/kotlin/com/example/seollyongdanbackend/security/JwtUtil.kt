package com.example.seollyongdanbackend.security


import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.util.*
import io.jsonwebtoken.security.Keys
import javax.crypto.SecretKey

//JWT를 생성, 검증, 해석하는 역할
@Component
class JwtUtil {
    private val SECRET_KEY: SecretKey = Keys.hmacShaKeyFor("mySecretKeyMySecretKeyMySecretKeyMySecretKey".toByteArray())

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token)
            println("Token is valid: ${claims.body.subject}") // ✅ 로그 추가
            true
        } catch (e: ExpiredJwtException) {
            println("JWT Token has expired") // ✅ 만료 로그
            false
        } catch (e: Exception) {
            println("Invalid JWT Token") // ✅ 기타 오류 로그
            false
        }
    }

    fun extractUsername(token: String): String {
        println("Parsing Token: $token") // ✅ 추가 로그
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}
