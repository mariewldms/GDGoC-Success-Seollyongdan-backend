package com.example.seollyongdanbackend.repository

import com.example.seollyongdanbackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    // 아이디(username)으로 사용자 조회 (로그인 시 필요)
    fun findByUsername(username: String): User?

    // 닉네임 중복 확인용
    fun findByNickname(nickname: String): User?

    // 특정 지역에 속한 사용자 조회
    fun findByTown(town: String): List<User>
}
