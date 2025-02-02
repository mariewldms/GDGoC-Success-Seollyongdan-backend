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

        // ✅ 로그인, 회원가입, 비밀번호 찾기 요청은 JWT 검증 제외
        if (requestURI.startsWith("/api/users/login") ||
            requestURI.startsWith("/api/users/signup") ||
            requestURI.startsWith("/api/users/reset-password")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = getTokenFromRequest(request)

            if (!token.isNullOrEmpty() && jwtUtil.validateToken(token)) {
                val username = jwtUtil.extractUsername(token)
                val userDetails = userService.loadUserByUsername(username)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: ExpiredJwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired")
            return
        } catch (e: MalformedJwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token")
            return
        } catch (e: UnsupportedJwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is unsupported")
            return
        } catch (e: IllegalArgumentException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT claims string is empty")
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
