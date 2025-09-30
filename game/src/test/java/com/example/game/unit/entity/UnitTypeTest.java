package com.example.game.unit.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTypeTest {

    @DisplayName("getRandomUnitType()함수는 null 을 반환해서는 안된다 ")
    @Test
    void getRandomUnitTypeNotNullTest() {
        // given
        // when
        UnitType unitType = UnitType.getRandomUnitType();

        // then
        assertNotNull(unitType, "getRandomUnitType()함수는 null 을 반환해서는 안된다");
    }

    @DisplayName("랜덤으로 선택된 UnitType은 enum 값 중 하나여야 한다.")
    @Test
    void getRandomUnitTypeTestTest() {
        // given
        // when
        UnitType unitType = UnitType.getRandomUnitType();
        boolean contains = false;
        for (UnitType t : UnitType.values()) {
            if (t == unitType) {
                contains = true;
                break;
            }
        }

        // then
        assertTrue(contains, "랜덤으로 선택된 UnitType은 enum 값 중 하나여야 한다.");
    }


}
