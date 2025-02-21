package com.example.seollyongbackend.security;

import com.example.seollyongbackend.service.UserService;
import com.example.seollyongbackend.util.JwtUtil; // ✅ 변경된 패키지 경로
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.ObjectProvider; // ✅ ObjectProvider 사용
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectProvider<UserService> userServiceProvider;

    // JWT 검사를 제외할 엔드포인트 목록
    private static final List<String> EXCLUDED_ENDPOINTS = List.of(
            "/api/users/login",
            "/api/users/signup",
            "/api/users/check-username",
            "/api/users/check-nickname"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectProvider<UserService> userServiceProvider) {
        this.jwtUtil = jwtUtil;
        this.userServiceProvider = userServiceProvider;
    }

    private UserService getUserService() {
        return userServiceProvider.getIfAvailable();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("Incoming Request URI: " + requestURI); // ✅ 요청 URI 로그 추가

        // 예외 처리할 엔드포인트인지 확인
        if (EXCLUDED_ENDPOINTS.contains(requestURI)) {
            System.out.println("Skipping JWT filter for: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = getTokenFromRequest(request);
            System.out.println("Extracted Token: " + token); // ✅ JWT 추출 로그

            if (token != null && jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                System.out.println("Extracted Username from JWT: " + username); // ✅ 사용자 이름 추출 로그

                UserDetails userDetails = getUserService().loadUserByUsername(username);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("User authenticated: " + username); // ✅ 인증 성공 로그
                }
            } else {
                System.out.println("Invalid JWT Token"); // ✅ JWT가 유효하지 않음
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired"); // ✅ JWT 만료 로그
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
            return;
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("JWT Authentication failed"); // ✅ 인증 실패 로그
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Authentication failed");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
