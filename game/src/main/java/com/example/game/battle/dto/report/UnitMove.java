package com.example.game.battle.dto.report;

import com.example.game.battle.dto.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class UnitMove {
    private long unitId;
    private Location unitMove;
    private Location unitAttack;
}
