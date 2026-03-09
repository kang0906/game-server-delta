package com.example.game.battle.dto;

import com.example.game.battle.entity.DeployUnit;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.enums.UnitType;
import com.example.game.user.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class BattleUnit {
    private Unit unit;
    private DeployUnit deployUnit;

    private User user;
    private long unitId;

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
        this.unitId = unit.getUnitId();

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
        this.unitId = unit.getDeployUnitId();

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

    public void action(List<BattleUnit> hostileUnitList, List<BattleUnit> friendlyUnitList) {
        BattleUnit nearestTarget = null;
        int minDist = Integer.MAX_VALUE;

        // 쿨타임 체크(이동, 공격)
        if (lastMove < moveSpeed && lastMove < attackSpeed) {
            lastMove++;
            return;
        }

        for (BattleUnit enemy : hostileUnitList) {
            int dist = this.getLocation().getDistance(enemy.getLocation());
            if (dist < minDist) {
                minDist = dist;
                nearestTarget = enemy;
            }
        }

        if (minDist == 1 && lastMove >= attackSpeed) {
            attack(nearestTarget, hostileUnitList);
        } else {
            moveTo(nearestTarget, hostileUnitList, friendlyUnitList);
        }
    }

    private void moveTo(BattleUnit target, List<BattleUnit> hostileUnitList, List<BattleUnit> friendlyUnitList) {

        Location targetPos = target.getLocation();
        log.debug("target = unitId : {}, pos : {}", target.unitId, target.getLocation());

        int x = location.getX();
        int y = location.getY();

        Location bestMove = location;
        int bestDist = location.getDistance(targetPos);

        int[][] directions = {
                {1, 0},   // right
                {-1, 0},  // left
                {0, -1},  // down
                {0, 1}    // up
        };

        for (int[] dir : directions) {

            int nx = x + dir[0];
            int ny = y + dir[1];

            // 맵 범위 체크
            if (nx < 0 || nx >= 8 || ny < 0 || ny >= 8){
                continue;
            }

            Location next = Location.values()[ny * 8 + nx];

            // 충돌 체크
            if (isOccupied(next, hostileUnitList, friendlyUnitList)){
//                log.debug("????????? ({}, {})", nx, ny);
                continue;
            }

            int dist = next.getDistance(targetPos);

            if (dist < bestDist) {
                bestDist = dist;
                bestMove = next;
            }
        }

        if (location == bestMove) {
            log.debug("unit blocked [unit: {}, location: {}]", this.getUnitId(), location.toString());
        }
        log.debug("unit(id: {}) move to {} => {}", this.unitId, location.toString(), bestMove);

        this.location = bestMove;
        lastMove = 0;
    }
    private boolean isOccupied(
            Location pos,
            List<BattleUnit> hostileUnitList,
            List<BattleUnit> friendlyUnitList) {

        for (BattleUnit u : hostileUnitList) {
            if (u.getLocation() == pos)
                return true;
        }

        for (BattleUnit u : friendlyUnitList) {
            if (u.getLocation() == pos)
                return true;
        }

        return false;
    }

    private void attack(BattleUnit target, List<BattleUnit> hostileUnitList) {
        target.hp = target.hp - (this.ap - target.getDef());

        log.debug("unit(id: {}) attack unit(id: {}, hp: {})", this.unitId, target.getUnitId(), target.getHp());
        if (target.hp <= 0) {
            hostileUnitList.remove(target);
            log.debug("unit(id: {}) eliminated , remain enemy: {}", target.getUnitId(), hostileUnitList.size());
        }

        lastMove = 0;
    }

    public BattleUnit reverseLocation() {
        this.location = location.flipVertical();
        return this;
    }
}
