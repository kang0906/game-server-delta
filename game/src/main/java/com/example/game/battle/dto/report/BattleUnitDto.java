package com.example.game.battle.dto.report;


import com.example.game.battle.dto.BattleUnit;
import com.example.game.battle.dto.Location;
import com.example.game.unit.entity.enums.UnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BattleUnitDto {
    private long unitId;

    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;
    private int level;
    private UnitType unitType;
    private Location location;

    public BattleUnitDto(BattleUnit battleUnit) {
        this.unitId = battleUnit.getUnitId();
        this.ap = battleUnit.getAp();
        this.def = battleUnit.getDef();
        this.hp = battleUnit.getHp();
        this.attackSpeed = battleUnit.getAttackSpeed();
        this.moveSpeed = battleUnit.getMoveSpeed();
        this.level = battleUnit.getLevel();
        this.unitType = battleUnit.getUnitType();
        this.location = battleUnit.getLocation();
    }
}
