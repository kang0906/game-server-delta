package com.example.game.unit.dto;

import com.example.game.unit.entity.UpgradeList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitUpgradeOptionsResponseDto {
    private String option1;
    private String option2;
    private String option3;

    public UnitUpgradeOptionsResponseDto(UpgradeList upgradeList) {
        this.option1 = upgradeList.getOption1().toString();
        this.option2 = upgradeList.getOption2().toString();
        this.option3 = upgradeList.getOption3().toString();
    }
}
