package com.example.game.battle.entity;

import com.example.game.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeploymentMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deploymentMapId;

    private long userId;
    private int mmr;

    @OneToMany(mappedBy = "deploymentMap", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DeployUnit> deployUnitList = new ArrayList<>();

    public DeploymentMap(List<Unit> unitList) {
        int mmr = 0;
        userId = unitList.get(0).getUser().getUserId();

        for (Unit unit : unitList) {
            deployUnitList.add(new DeployUnit(unit, this));
            mmr += unit.getLevel();
        }

        this.mmr = mmr;
    }
}
