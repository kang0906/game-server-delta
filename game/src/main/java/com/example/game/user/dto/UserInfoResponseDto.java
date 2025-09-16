package com.example.game.user.dto;

import com.example.game.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private String username;

    public UserInfoResponseDto(User user) {
        this.username = user.getUsername();
    }
}
