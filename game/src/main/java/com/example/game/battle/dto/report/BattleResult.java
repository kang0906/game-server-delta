package com.example.game.battle.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BattleResult {

    private boolean userWin;
    private int remainUserUnit;
    private int remainHostileUnit;
}
