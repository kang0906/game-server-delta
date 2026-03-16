package com.example.game.battle.dto.report;

import com.example.game.battle.dto.BattleUnit;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BattleReport {
    private Long userId;
    private Long deploymentMapId;
    private LocalDateTime createAt;
    private int mmr;
    private int calcTime;

    private List<BattleUnitDto> userUnitList;
    private List<BattleUnitDto> hostileUnitList;

    private List<MovePerRound> movePerRoundList = new ArrayList<>();

    private BattleResult battleResult;

    public BattleReport(Long userId, Long deploymentMapId, int mmr, List<BattleUnit> userUnitList, List<BattleUnit> hostileUnitList) {
        this.userId = userId;
        this.deploymentMapId = deploymentMapId;
        this.createAt = LocalDateTime.now();
        this.mmr = mmr;
        this.userUnitList = userUnitList.stream().map(BattleUnitDto::new).toList();
        this.hostileUnitList = hostileUnitList.stream().map(BattleUnitDto::new).toList();
    }

    public void addMovePerRound(MovePerRound movePerRound) {
        movePerRoundList.add(movePerRound);
    }

    public void addBattleResult(BattleResult battleResult) {
        this.battleResult = battleResult;
    }
}
