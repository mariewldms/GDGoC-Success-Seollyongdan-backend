package com.example.seollyongdanbackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val username: String, //사용자 아이디

    @Column(nullable = false)
    val password: String, //사용자 비번

    @Column(unique = true, nullable = false)
    val nickname: String, //닉네임

    @Column(nullable = false)
    val town: String, // 사용자가 선택한 동네 정보

    @Transient // DB에 저장되지 않도록 설정
    var confirmPassword: String? = null


)
