package com.example.game.unit.entity.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum UnitType {

    INFANTRY("infantry", 30, 10, 100, 5, 15,
                                3, 1, 10, 1, 1),
    ARTILLERY("artillery", 50, 5, 50, 15, 25,
                                5, 1, 5, 1, 1),
    CAVALRY("cavalry", 40, 20, 100, 10, 5,
                            5, 1, 5, 1, 1)
    ;

    private String name;
    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;

    private int apUpgradeWeight;
    private int defUpgradeWeight;
    private int hpUpgradeWeight;
    private int attackSpeedUpgradeWeight;
    private int moveSpeedUpgradeWeight;

    private static final Random random = new Random();
    public static UnitType getRandomUnitType() {
        UnitType[] values = values();
        return values[random.nextInt(values.length)];
    }

    UnitType(String name, int ap, int def, int hp, int attackSpeed, int moveSpeed,
             int apUpgradeWeight, int defUpgradeWeight, int hpUpgradeWeight, int attackSpeedUpgradeWeight, int moveSpeedUpgradeWeight) {
        this.name = name;
        this.ap = ap;
        this.def = def;
        this.hp = hp;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed;

        this.apUpgradeWeight = apUpgradeWeight;
        this.defUpgradeWeight = defUpgradeWeight;
        this.hpUpgradeWeight = hpUpgradeWeight;
        this.attackSpeedUpgradeWeight = attackSpeedUpgradeWeight;
        this.moveSpeedUpgradeWeight = moveSpeedUpgradeWeight;
    }
}
