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


}
