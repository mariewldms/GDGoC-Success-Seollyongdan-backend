package com.example.seollyongbackend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("mySecretKeyMySecretKeyMySecretKeyMySecretKey".getBytes());

    // ✅ JWT 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ JWT 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("Token is valid: " + claims.getBody().getSubject()); // ✅ 로그 추가
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired"); // ✅ 만료 로그
        } catch (Exception e) {
            System.out.println("Invalid JWT Token"); // ✅ 기타 오류 로그
        }
        return false;
    }

    // ✅ JWT에서 사용자명 추출
    public String extractUsername(String token) {
        System.out.println("Parsing Token: " + token); // ✅ 추가 로그
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}