package com.example.game.user.dto;

import com.example.game.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private int money;

    public UserInfoResponseDto(User user) {
        this.money = user.getUserGameInfo().getMoney();
    }
}
