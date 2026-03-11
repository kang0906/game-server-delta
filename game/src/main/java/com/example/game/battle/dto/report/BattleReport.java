package com.example.game.battle.dto.report;

import com.example.game.battle.dto.BattleUnit;
import lombok.Getter;

import java.util.List;

@Getter
public class BattleReport {
    private Long BattleReportId;
    private List<BattleUnit> userUnitList;
    private List<BattleUnit> hostileUnitList;

    private List<MovePerRound> movePerRoundList;

    private BattleResult battleResult;
}
