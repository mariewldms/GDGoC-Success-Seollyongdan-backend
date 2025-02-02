package com.example.seollyongdanbackend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

//사용자가 회원가입을 요청할 때 전달하는 데이터 담는 클래스
data class UserRequest(

    @field:NotBlank
    @field:Size(min = 4, max = 12)
    val username: String, // 아이디 (로그인 시 사용)

    @field:NotBlank
    @field:Size(min = 4, max = 12)
    val password: String, // 비밀번호

    @field:NotBlank
    val nickname: String, // 닉네임

    @field:NotBlank
    val town: String, // 사용자의 거주지 설정 (기존 defaultSetting 변경)

)
