package com.example.seollyongbackend.service;

import com.example.seollyongbackend.config.SecurityConfig;
import com.example.seollyongbackend.entity.User;
import com.example.seollyongbackend.repository.UserRepository;
import com.example.seollyongbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // ✅ 회원가입 처리
    public String registerUser(String username, String password, String nickname, String town) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, hashedPassword, nickname, town);
        userRepository.save(newUser);
        return "회원가입이 완료되었습니다.";
    }

    // ✅ 로그인 & JWT 발급
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "정보가 일치하지 않습니다.");
        }
        return jwtUtil.generateToken(username);
    }

    // ✅ 아이디 찾기 (닉네임으로 검색)
    public String findUsernameByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            throw new IllegalArgumentException("해당 닉네임을 가진 사용자가 없습니다.");
        }
        return user.getUsername();
    }

    // ✅ JWT에서 사용자 이름 추출
    public String extractUsernameFromToken(String token) {
        String cleanToken = token.replace("Bearer ", ""); // Bearer 제거
        System.out.println("Extracted Token: " + cleanToken); // ✅ 로그 추가
        String username = jwtUtil.extractUsername(cleanToken);
        System.out.println("Extracted Username: " + username); // ✅ 로그 추가
        return username;
    }

    // ✅ 비밀번호 찾기: 아이디 입력만으로 새로운 비밀번호 변경
    public String resetPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다.");
        }
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedNewPassword);
        userRepository.save(user);
        return "비밀번호가 성공적으로 변경되었습니다.";
    }

    // ✅ Spring Security의 `UserDetailsService` 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    // ✅ 아이디 중복 확인
    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    // ✅ 닉네임 중복 확인
    public boolean isNicknameAvailable(String nickname) {
        return userRepository.findByNickname(nickname) == null;
    }
}