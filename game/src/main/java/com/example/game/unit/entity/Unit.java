package com.example.game.unit.entity;

import com.example.game.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    @Enumerated(EnumType.STRING)
    private Deploy deploy;
    @Embedded
    private UpgradeList upgradeList;

    public Unit(User user) {
        UnitType unitType = UnitType.getRandomUnitType();

        this.user = user;
        this.ap = unitType.getAp();
        this.def = unitType.getAp();
        this.hp = unitType.getHp();
        this.attackSpeed = unitType.getAttackSpeed();
        this.moveSpeed = unitType.getMoveSpeed();
        this.unitType = unitType;
        this.deploy = null;
    }

    public void deployUnit(Deploy deploy) {
        this.deploy = deploy;
    }
}
