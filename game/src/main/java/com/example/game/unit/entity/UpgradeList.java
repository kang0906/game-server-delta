package com.example.game.unit.entity;

import com.example.game.unit.entity.enums.UpgradeOption;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpgradeList {
    @Enumerated(EnumType.STRING)
    private UpgradeOption option1;
    @Enumerated(EnumType.STRING)
    private UpgradeOption option2;
    @Enumerated(EnumType.STRING)
    private UpgradeOption option3;

    public boolean checkIfOptionNotMatch(UpgradeOption upgradeOption) {
        if(upgradeOption == option1 || upgradeOption == option2 || upgradeOption == option3) {
            return false;
        }
        return true;
    }
}
