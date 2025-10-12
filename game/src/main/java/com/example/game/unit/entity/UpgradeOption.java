package com.example.game.unit.entity;

import lombok.Getter;

@Getter
public enum UpgradeOption {

    INFANTRY_AP(5),
    INFANTRY_DEF(5),
    INFANTRY_HP(5),
    INFANTRY_ATTACK_SPEED(5),
    INFANTRY_MOVE_SPEED(5),
    ARTILLERY_AP(5),
    ARTILLERY_DEF(5),
    ARTILLERY_HP(5),
    ARTILLERY_ATTACK_SPEED(5),
    ARTILLERY_MOVE_SPEED(5),
    CAVALRY_AP(5),
    CAVALRY_DEF(5),
    CAVALRY_HP(5),
    CAVALRY_ATTACK_SPEED(5),
    CAVALRY_MOVE_SPEED(5)
    ;

    private int value;

    UpgradeOption(int value) {
        this.value = value;
    }
}
