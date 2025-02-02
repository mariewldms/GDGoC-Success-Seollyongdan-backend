package com.example.seollyongdanbackend.controller

import com.example.seollyongdanbackend.dto.UserRequest
import com.example.seollyongdanbackend.dto.AuthResponse
import com.example.seollyongdanbackend.dto.UserLoginRequest
import com.example.seollyongdanbackend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    // ✅ 회원가입
    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody request: UserRequest): ResponseEntity<String> {
        val message = userService.registerUser(
            username = request.username,
            password = request.password,
            nickname = request.nickname,
            town = request.town
        )
        return ResponseEntity.ok(message)
    }

    // ✅ 로그인 (JWT 발급)
    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): ResponseEntity<AuthResponse> {
        val token = userService.login(request.username, request.password)
        return ResponseEntity.ok(AuthResponse(token))
    }

    // ✅ 아이디 찾기 (닉네임 기반)
    @GetMapping("/find-username")
    fun findUsername(@RequestParam nickname: String): ResponseEntity<String> {
        val username = userService.findUsernameByNickname(nickname)
        return ResponseEntity.ok(username)
    }

    // ✅ 비밀번호 변경 API (사용자가 비밀번호를 변경할 때, JWT 필요)
    @PostMapping("/change-password")
    fun changePassword(
        @RequestParam username: String,
        @RequestParam currentPassword: String,
        @RequestParam newPassword: String
    ): ResponseEntity<String> {
        val message = userService.changePassword(username, currentPassword, newPassword)
        return ResponseEntity.ok(message)
    }

    // ✅ 아이디 중복 확인 (토큰 없이 사용 가능)
    @GetMapping("/check-username")
    fun checkUsername(@RequestParam username: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.isUsernameAvailable(username))
    }

    // ✅ 닉네임 중복 확인 (토큰 없이 사용 가능)
    @GetMapping("/check-nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.isNicknameAvailable(nickname))
    }
}
