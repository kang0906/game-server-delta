package com.example.game.unit.entity;

import java.util.Random;

public enum UnitType {

    INFANTRY("infantry", 30, 10, 100, 5, 15),
    ARTILLERY("artillery", 50, 5, 50, 15, 25),
    CAVALRY("cavalry", 40, 20, 100, 10, 5)
    ;

    private String name;
    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;

    private static final Random random = new Random();
    public static UnitType getRandomUnitType() {
        UnitType[] values = values();
        return values[random.nextInt(values.length)];
    }

    UnitType(String name, int ap, int def, int hp, int attackSpeed, int moveSpeed) {
        this.name = name;
        this.ap = ap;
        this.def = def;
        this.hp = hp;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed;
    }
}
