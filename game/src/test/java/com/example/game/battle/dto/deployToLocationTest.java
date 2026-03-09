package com.example.game.battle.dto;

import com.example.game.unit.entity.enums.Deploy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class deployToLocationTest {

    @DisplayName("Deploy 객체를 성공적으로 Location 객체로 변환한다.")
    @Test
    void deployToLocationTest() {
        // given

        // when
        Location location = Location.deployToLocation(Deploy.A1);

        // then
        Assertions.assertThat(location).isEqualTo(Location.A1);
    }

    @DisplayName("Location 수직 반전 함수 테스트")
    @Test
    void flipVerticalTest() {
        // given
        Location[] values = Location.values();

        // when
        for (Location value : values) {
            System.out.println(value + "=>" + value.flipVertical());
        }

        // then

    }

}
