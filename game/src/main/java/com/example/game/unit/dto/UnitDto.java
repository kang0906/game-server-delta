package com.example.game.unit.dto;

import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.enums.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitDto {
    private Long unitId;

    private Long userId;

    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;
    private UnitType unitType;
    private Deploy deploy;

    public UnitDto(Unit unit) {
        this.unitId = unit.getUnitId();
        this.userId = unit.getUser().getUserId();
        this.ap = unit.getAp();
        this.def = unit.getDef();
        this.hp = unit.getHp();
        this.attackSpeed = unit.getAttackSpeed();
        this.moveSpeed = unit.getMoveSpeed();
        this.unitType = unit.getUnitType();
        this.deploy = unit.getDeploy();
    }
}
