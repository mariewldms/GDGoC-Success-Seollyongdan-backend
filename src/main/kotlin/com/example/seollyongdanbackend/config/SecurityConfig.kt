package com.example.seollyongdanbackend.config

import org.springframework.context.annotation.Lazy
import com.example.seollyongdanbackend.security.JwtAuthenticationFilter
import com.example.seollyongdanbackend.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    @Lazy private val userService: UserService
) {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource { request -> CorsConfiguration().applyPermitDefaultValues() } } // ✅ CORS 문제 방지 (필요 시 활성화)
            .csrf { it.disable() } // ✅ CSRF 비활성화 (JWT 사용 시 필요)
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "/api/users/signup",
                    "/api/users/login",
                    "/api/users/find-username",
                    "/api/users/reset-password",
                    "/api/users/check-username",
                    "/api/users/check-nickname"
                ).permitAll() // ✅ 인증 없이 접근 가능

                auth.anyRequest().authenticated() // ✅ 나머지 요청은 인증 필요 (JWT 토큰 필요)
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint()) // 401 반환
                exceptionHandling.accessDeniedHandler(customAccessDeniedHandler()) // 403 처리
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ✅ 세션 상태 관리 (JWT 사용)
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java) // ✅ JWT 인증 필터 추가

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")  // 모든 도메인 허용
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun customAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request, response, authException ->
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "정보가 일치하지 않습니다.") // 401 반환
        }
    }

    @Bean
    fun customAccessDeniedHandler(): AccessDeniedHandler {
        return AccessDeniedHandler { request, response, accessDeniedException ->
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근이 거부되었습니다.") // 403 반환
        }
    }

}
