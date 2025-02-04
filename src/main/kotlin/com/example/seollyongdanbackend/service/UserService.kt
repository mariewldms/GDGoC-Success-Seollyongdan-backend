package com.example.seollyongdanbackend.service

import com.example.seollyongdanbackend.entity.User
import com.example.seollyongdanbackend.repository.UserRepository
import com.example.seollyongdanbackend.security.JwtUtil
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) : UserDetailsService {

    @Lazy
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    // 회원가입 처리 (confirmPassword 필드 제거)
    fun registerUser(username: String, password: String, nickname: String, town: String): String {
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("이미 존재하는 아이디입니다.")
        }
        if (userRepository.findByNickname(nickname) != null) {
            throw IllegalArgumentException("이미 사용 중인 닉네임입니다.")
        }

        val hashedPassword = passwordEncoder.encode(password)
        val newUser = User(
            username = username,
            password = hashedPassword,
            nickname = nickname,
            town = town
        )
        userRepository.save(newUser)
        return "회원가입이 완료되었습니다."
    }

    // 로그인 & JWT 발급
    fun login(username: String, password: String): String {
        val user = userRepository.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "정보가 일치하지 않습니다.")

        if (!passwordEncoder.matches(password, user.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "정보가 일치하지 않습니다.")
        }

        return jwtUtil.generateToken(username)
    }

    // 아이디 찾기 (닉네임으로 검색)
    fun findUsernameByNickname(nickname: String): String {
        val user = userRepository.findByNickname(nickname) ?: throw IllegalArgumentException("해당 닉네임을 가진 사용자가 없습니다.")
        return user.username
    }

    // 비밀번호 찾기: 아이디 입력만으로 새로운 비밀번호 변경
    fun resetPassword(username: String, newPassword: String): String {
        val user = userRepository.findByUsername(username) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다.")
        val hashedNewPassword = passwordEncoder.encode(newPassword)
        val updatedUser = user.copy(password = hashedNewPassword)
        userRepository.save(updatedUser)

        return "비밀번호가 성공적으로 변경되었습니다."
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $username")
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.username)
            .password(user.password)
            .roles("USER")
            .build()
    }

    fun isUsernameAvailable(username: String): Boolean {
        return userRepository.findByUsername(username) == null
    }

    fun isNicknameAvailable(nickname: String): Boolean {
        return userRepository.findByNickname(nickname) == null
    }
}
