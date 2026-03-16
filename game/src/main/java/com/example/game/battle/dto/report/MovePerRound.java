package com.example.game.battle.dto.report;

import com.example.game.battle.dto.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class MovePerRound {
    private int round;
    private List<UnitMove> unitMoveList = new ArrayList<>();

    public boolean isPresent() {
        return !unitMoveList.isEmpty();
    }

    public MovePerRound(int round) {
        this.round = round;
    }

    public void addUnitMove(Optional<UnitMove> unitMove) {
        if (unitMove.isPresent()) {
            unitMoveList.add(unitMove.get());
        }
    }
}
