package com.example.seollyongbackend.dto;

import com.example.seollyongbackend.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String town;
    private boolean resident;

    public UserInfoResponseDto(User user) {
        this.id=user.getId();
        this.username=user.getUsername();
        this.nickname=user.getNickname();
        this.resident=user.isResident();
        this.town=user.getTown();
    }

}
