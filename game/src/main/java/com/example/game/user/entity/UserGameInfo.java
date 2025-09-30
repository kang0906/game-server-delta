package com.example.game.user.entity;

import com.example.game.common.exception.ErrorCode;
import com.example.game.common.exception.GlobalException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserGameInfo {

    private int money;

    public void addMoney(int amount) {
        if (amount < 0) {
            throw new GlobalException(ErrorCode.CAN_NOT_USE_NEGATIVE_NUMBER);
        }
        money += amount;
    }

    public void useMoney(int amount) {
        if (amount < 0) {
            throw new GlobalException(ErrorCode.CAN_NOT_USE_NEGATIVE_NUMBER);
        }
        if(amount > money) {
            throw new GlobalException(ErrorCode.NOT_ENOUGH_MONEY);
        }
        money -= amount;
    }
}
