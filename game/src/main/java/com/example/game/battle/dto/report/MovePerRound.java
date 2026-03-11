package com.example.game.battle.dto.report;

import com.example.game.battle.dto.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovePerRound {
    private int unitId;
    private Location unitMove;
    private Location unitAttack;
}
