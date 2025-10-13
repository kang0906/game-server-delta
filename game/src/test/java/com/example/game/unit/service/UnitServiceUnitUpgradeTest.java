package com.example.game.unit.service;

import com.example.game.unit.dto.UnitUpgradeResponseDto;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.enums.UpgradeOption;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.entity.UserGameInfo;
import com.example.game.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UnitServiceUnitUpgradeTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;

    @DisplayName("유닛 업그레이드 정보를 정상적으로 조회한다.")
    @Test
    void getUnitUpgradeListTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));

        for(int i = 0; i< 100; i++) {
            unit.setUpgradeList();

            // when
            UnitUpgradeResponseDto unitUpgradeList = unitService.getUnitUpgradeList(user, unit.getUnitId());

            //then
            assertThat(unitUpgradeList).isNotNull();


            assertThat(unitUpgradeList.getOption1()).isNotNull()
                    .containsAnyOf(Arrays.stream(UpgradeOption.values()).map(Enum::name).toArray(String[]::new));

            assertThat(unitUpgradeList.getOption2()).isNotNull()
                    .containsAnyOf(Arrays.stream(UpgradeOption.values()).map(Enum::name).toArray(String[]::new));

            assertThat(unitUpgradeList.getOption3()).isNotNull()
                    .containsAnyOf(Arrays.stream(UpgradeOption.values()).map(Enum::name).toArray(String[]::new));


            assertThat(unitUpgradeList.getOption1())
                    .isNotEqualTo(unitUpgradeList.getOption2());

            assertThat(unitUpgradeList.getOption2())
                    .isNotEqualTo(unitUpgradeList.getOption3());

            assertThat(unitUpgradeList.getOption1())
                    .isNotEqualTo(unitUpgradeList.getOption3());
        }
    }

}
