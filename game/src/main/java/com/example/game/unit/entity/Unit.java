package com.example.game.unit.entity;

import com.example.game.common.exception.ErrorCode;
import com.example.game.common.exception.GlobalException;
import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.entity.enums.UnitType;
import com.example.game.unit.entity.enums.UpgradeOption;
import com.example.game.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        this.ap = 0;
        this.def = 0;
        this.hp = 0;
        this.attackSpeed = 0;
        this.moveSpeed = 0;
        this.unitType = unitType;
        this.deploy = null;
    }

    public void deployUnit(Deploy deploy) {
        this.deploy = deploy;
    }

    public void setUpgradeList() {
        ArrayList<UpgradeOption> upgradeOptions = new ArrayList<>(List.of(UpgradeOption.values()));
        Collections.shuffle(upgradeOptions);
        this.upgradeList = new UpgradeList(upgradeOptions.get(0), upgradeOptions.get(1), upgradeOptions.get(2));
    }
}
