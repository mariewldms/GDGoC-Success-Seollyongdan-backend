package com.example.seollyongbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {

    @NotBlank
    @Size(min = 4, max = 12)
    private String username; // 아이디 (로그인 시 사용)

    @NotBlank
    @Size(min = 4, max = 12)
    private String password; // 비밀번호

    @NotBlank
    private String nickname; // 닉네임

    @NotBlank
    private String town; // 사용자의 거주지 설정

    public UserRequest() {}

    public UserRequest(String username, String password, String nickname, String town) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.town = town;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public String getTown() { return town; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setTown(String town) { this.town = town; }
}