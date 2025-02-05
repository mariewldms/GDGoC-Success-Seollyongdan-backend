package com.example.seollyongdanbackend.security

import com.example.seollyongdanbackend.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.context.annotation.Lazy

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    @Lazy private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI
        println("Incoming Request URI: $requestURI") // ✅ 요청 URI 로그 추가

        if (requestURI.startsWith("/api/users/login") ||
            requestURI.startsWith("/api/users/signup") ||
            requestURI.startsWith("/api/users/check-username") ||
            requestURI.startsWith("/api/users/check-nickname")
        ) {
            println("Skipping JWT filter for: $requestURI") // ✅ 인증 제외된 경로 로그
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = getTokenFromRequest(request)
            println("Extracted Token: $token") // ✅ JWT 추출 로그

            if (!token.isNullOrEmpty() && jwtUtil.validateToken(token)) {
                val username = jwtUtil.extractUsername(token)
                println("Extracted Username from JWT: $username") // ✅ 사용자 이름 추출 로그

                val userDetails = userService.loadUserByUsername(username)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                    println("User authenticated: $username") // ✅ 인증 성공 로그
                }
            } else {
                println("Invalid JWT Token") // ✅ JWT가 유효하지 않음
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token")
                return
            }
        } catch (e: ExpiredJwtException) {
            println("JWT Token has expired") // ✅ JWT 만료 로그
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired")
            return
        } catch (e: Exception) {
            println("JWT Authentication failed") // ✅ 인증 실패 로그
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Authentication failed")
            return
        }

        filterChain.doFilter(request, response)
    }


    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
