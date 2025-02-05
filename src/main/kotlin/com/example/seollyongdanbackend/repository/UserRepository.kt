package com.example.seollyongdanbackend.repository

import com.example.seollyongdanbackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository //기본적인 CRUD 기능 제공
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    // 아이디 중복 확인용
    fun findByUsername(username: String): User?

    // 닉네임 중복 확인용
    fun findByNickname(nickname: String): User?

}