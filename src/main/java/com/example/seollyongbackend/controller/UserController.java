package com.example.seollyongbackend.controller;


import com.example.seollyongbackend.dto.UserRequest;
import com.example.seollyongbackend.dto.AuthResponse;
import com.example.seollyongbackend.dto.UserLoginRequest;
import com.example.seollyongbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest request) {
        String message = userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getNickname(),
                request.getTown()
        );
        return ResponseEntity.ok(message);
    }

    // ✅ 로그인 (JWT 발급)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse("비밀번호를 입력해주세요."));
        }
        String token = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // ✅ 아이디 찾기 (닉네임 기반) → JWT 인증 추가
    @GetMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestHeader("Authorization") String token) {
        String username = userService.extractUsernameFromToken(token);
        return ResponseEntity.ok(username);
    }

    // ✅ 비밀번호 변경 API → JWT 인증 추가
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String token,
                                                @RequestBody ResetPasswordRequest request) {
        String username = userService.extractUsernameFromToken(token);
        String message = userService.resetPassword(username, request.getNewPassword());
        return ResponseEntity.ok(message);
    }

    // ✅ 아이디 중복 확인 (토큰 없이 사용 가능)
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.isUsernameAvailable(username));
    }

    // ✅ 닉네임 중복 확인 (토큰 없이 사용 가능)
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.isNicknameAvailable(nickname));
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
}