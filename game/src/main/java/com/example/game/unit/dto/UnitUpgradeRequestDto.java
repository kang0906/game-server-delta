package com.example.game.unit.dto;

import com.example.game.unit.entity.enums.UpgradeOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitUpgradeRequestDto {
    public String stat;

    public UpgradeOption getUpgradeOptionEnumByString() {
        return UpgradeOption.valueOf(stat);
    }
}
