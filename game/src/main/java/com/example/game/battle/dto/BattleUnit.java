package com.example.game.battle.dto;

import com.example.game.battle.entity.DeployUnit;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.enums.UnitType;
import com.example.game.user.entity.User;

public class BattleUnit {
    private Unit unit;
    private DeployUnit deployUnit;

    private User user;

    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;
    private int level;
    private UnitType unitType;
    private Location location;
    private int lastMove;

    public BattleUnit(Unit unit) {
        this.unit = unit;
        this.user = unit.getUser();
        this.unitType = unit.getUnitType();
        this.level = unit.getLevel();
        this.location = Location.deployToLocation(unit.getDeploy());

        this.ap = unitType.getAp() + (unitType.getApUpgradeWeight() * unit.getAp());
        this.def = unitType.getDef() + (unitType.getDefUpgradeWeight() * unit.getDef());
        this.hp = unitType.getHp() + (unitType.getHpUpgradeWeight() * unit.getHp());
        this.attackSpeed = unitType.getAttackSpeed() - (unitType.getAttackSpeedUpgradeWeight() * unit.getAttackSpeed());
        this.moveSpeed = unitType.getMoveSpeed() - (unitType.getMoveSpeedUpgradeWeight() * unit.getMoveSpeed());
    }

    public BattleUnit(DeployUnit unit) {
        this.deployUnit = unit;
        this.unitType = unit.getUnitType();
        this.level = unit.getLevel();
        this.location = Location.deployToLocation(unit.getDeploy());

        this.ap = unitType.getAp() + (unitType.getApUpgradeWeight() * unit.getAp());
        this.def = unitType.getDef() + (unitType.getDefUpgradeWeight() * unit.getDef());
        this.hp = unitType.getHp() + (unitType.getHpUpgradeWeight() * unit.getHp());
        this.attackSpeed = unitType.getAttackSpeed() - (unitType.getAttackSpeedUpgradeWeight() * unit.getAttackSpeed());
        this.moveSpeed = unitType.getMoveSpeed() - (unitType.getMoveSpeedUpgradeWeight() * unit.getMoveSpeed());
    }
}
