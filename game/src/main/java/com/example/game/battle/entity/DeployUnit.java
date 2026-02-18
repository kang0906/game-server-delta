package com.example.game.battle.entity;

import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.UpgradeList;
import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.entity.enums.UnitType;
import com.example.game.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeployUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deployUnitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deploymentMap_id")
    private DeploymentMap deploymentMap;

    private int ap;
    private int def;
    private int hp;
    private int attackSpeed;
    private int moveSpeed;
    private int level;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    @Enumerated(EnumType.STRING)
    private Deploy deploy;

    protected DeployUnit(Unit unit, DeploymentMap deploymentMap) {
        this.ap = unit.getAp();
        this.def = unit.getDef();
        this.hp = unit.getHp();
        this.attackSpeed = unit.getAttackSpeed();
        this.moveSpeed = unit.getMoveSpeed();
        this.level = unit.getLevel();
        this.unitType = unit.getUnitType();
        this.deploy = unit.getDeploy();
        this.deploymentMap = deploymentMap;
    }
}


