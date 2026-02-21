package com.example.game.unit.service;

import com.example.game.common.exception.ErrorCode;
import com.example.game.common.exception.GlobalException;
import com.example.game.unit.dto.UnitUpgradeOptionsResponseDto;
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

@SpringBootTest
@Transactional
class UnitServiceUnitUpgradeTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;

    @DisplayName("유닛을 정상적으로 업그레이드 한다.")
    @Test
    void unitUpgradeTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));
        unit.levelUp();
        String option1 = unitService.getUnitUpgradeList(user, unit.getUnitId()).getOption1();

        // when
        unitService.unitUpgrade(user, unit.getUnitId(), UpgradeOption.valueOf(option1));

        // then
        int unitStat = unit.getAp() + unit.getHp() + unit.getDef() + unit.getAttackSpeed() + unit.getMoveSpeed();
        assertThat(unitStat).isEqualTo(1);
        assertThat(unit.getUpgradeList()).isNull();
        assertThat(unit.getLevel()).isEqualTo(1);
    }

    @DisplayName("유닛의 소유자가 아닐경우 예외가 발생한다.")
    @Test
    void unitUpgradeFailByUserCheckTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        User user2 = userRepository.save(new User("testUser2", 2L, "testUser2", "testUser2", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));
        unit.levelUp();
        String option1 = unitService.getUnitUpgradeList(user, unit.getUnitId()).getOption1();

        // when then
        assertThatThrownBy(() ->unitService.unitUpgrade(user2, unit.getUnitId(), UpgradeOption.valueOf(option1)))
                .isInstanceOf(GlobalException.class)
                .hasMessage(ErrorCode.VALIDATION_FAIL.getMessage());
    }

    @DisplayName("유닛이 업그레이드 준비가 안된경우 예외가 발생한다.(업그레이드 두번 시도)")
    @Test
    void unitUpgradeFailByRequestDuplicateTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));
        unit.levelUp();
        String option1 = unitService.getUnitUpgradeList(user, unit.getUnitId()).getOption1();
        unitService.unitUpgrade(user, unit.getUnitId(), UpgradeOption.valueOf(option1));

        // when then
        assertThatThrownBy(() ->unitService.unitUpgrade(user, unit.getUnitId(), UpgradeOption.valueOf(option1)))
                .isInstanceOf(GlobalException.class)
                .hasMessage(ErrorCode.CAN_NOT_UPGRADE.getMessage());
    }

    @DisplayName("유닛이 업그레이드 준비가 안된경우 예외가 발생한다.(아무스탯이나 업그레이드 시도)")
    @Test
    void unitUpgradeFailByUpgradeNotReadyTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));

        // when then
        assertThatThrownBy(() ->unitService.unitUpgrade(user, unit.getUnitId(), UpgradeOption.AP))
                .isInstanceOf(GlobalException.class)
                .hasMessage(ErrorCode.CAN_NOT_UPGRADE.getMessage());
    }


    @DisplayName("유닛 업그레이드 정보를 정상적으로 조회한다.")
    @Test
    void getUnitUpgradeListTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));

        for(int i = 0; i< 100; i++) {
            unit.levelUp();

            // when
            UnitUpgradeOptionsResponseDto unitUpgradeList = unitService.getUnitUpgradeList(user, unit.getUnitId());

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

    @DisplayName("(레벨 > 스탯합)의 경우 업그레이드 목록을 새로 생성해 준다.")
    @Test
    void unitUpgradeOverLevelCheckTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));
        unit.levelUp();
        unit.levelUp();
        String option1 = unitService.getUnitUpgradeList(user, unit.getUnitId()).getOption1();

        // when
        unitService.unitUpgrade(user, unit.getUnitId(), UpgradeOption.valueOf(option1));

        // then
        int unitStat = unit.getAp() + unit.getHp() + unit.getDef() + unit.getAttackSpeed() + unit.getMoveSpeed();
        assertThat(unitStat).isEqualTo(1);
        assertThat(unit.getUpgradeList()).isNotNull();
        assertThat(unit.getLevel()).isEqualTo(2);


        assertThat(unit.getUpgradeList().getOption1().toString()).isNotNull()
                .containsAnyOf(Arrays.stream(UpgradeOption.values()).map(Enum::name).toArray(String[]::new));

        assertThat(unit.getUpgradeList().getOption2().toString()).isNotNull()
                .containsAnyOf(Arrays.stream(UpgradeOption.values()).map(Enum::name).toArray(String[]::new));

        assertThat(unit.getUpgradeList().getOption3().toString()).isNotNull()
                .containsAnyOf(Arrays.stream(UpgradeOption.values()).map(Enum::name).toArray(String[]::new));


        assertThat(unit.getUpgradeList().getOption1())
                .isNotEqualTo(unit.getUpgradeList().getOption2());

        assertThat(unit.getUpgradeList().getOption2())
                .isNotEqualTo(unit.getUpgradeList().getOption3());

        assertThat(unit.getUpgradeList().getOption1())
                .isNotEqualTo(unit.getUpgradeList().getOption3());
    }

}
