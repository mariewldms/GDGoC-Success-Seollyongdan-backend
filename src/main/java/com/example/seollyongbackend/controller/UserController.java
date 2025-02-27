package com.example.seollyongbackend.controller;

import com.example.seollyongbackend.dto.*;
import com.example.seollyongbackend.entity.User;
import com.example.seollyongbackend.repository.UserRepository;
import com.example.seollyongbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // ✅ 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody UserRequest request) {
        try {
            String message = userService.registerUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getNickname(),
                    request.getTown()
            );
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, message));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    // ✅ 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody UserLoginRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요."));
        }

        try {
            String token = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, new AuthResponse(token)));
        } catch (ResponseStatusException e) {
            // 예외가 발생하면 401 Unauthorized 응답 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "정보가 일치하지 않습니다."));
        }
    }


    // ✅ 아이디 찾기 (닉네임 기반)
    @GetMapping("/find-username")
    public ResponseEntity<ApiResponse<String>> findUsername(@RequestHeader("Authorization") String token) {
        String username = userService.extractUsernameFromToken(token);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, username));
    }

    // ✅ 비밀번호 변경
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestHeader("Authorization") String token,
                                                             @RequestBody ResetPasswordRequest request) {
        String username = userService.extractUsernameFromToken(token);
        String message = userService.resetPassword(username, request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, message));
    }

    // ✅ 아이디 중복 확인
    @GetMapping("/check-username")
    public ResponseEntity<ApiResponse<String>> checkUsername(@RequestParam String username) {
        boolean isAvailable = userService.isUsernameAvailable(username);

        if (isAvailable) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "사용 가능한 아이디입니다."));
        } else {
            return ResponseEntity.ok(ApiResponse.error(HttpStatus.OK, "이미 있는 아이디입니다."));
        }
    }


    // ✅ 닉네임 중복 확인
    @GetMapping("/check-nickname")
    public ResponseEntity<ApiResponse<String>> checkNickname(@RequestParam String nickname) {
        boolean isAvailable = userService.isNicknameAvailable(nickname);
        if (isAvailable) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "사용 가능한 닉네임입니다."));
        } else {
            return ResponseEntity.ok(ApiResponse.error(HttpStatus.OK, "이미 있는 닉네임입니다."));
        }
    }

    // ✅ 비밀번호 변경 요청 DTO
    public static class ResetPasswordRequest {
        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<UserInfoResponseDto>> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            // UserDetails로부터 username을 가져와 다시 User 조회
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByUsername(username);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, new UserInfoResponseDto(user)));
        } else {
            throw new RuntimeException("유효하지 않은 사용자 정보입니다.");
        }
    }
}

