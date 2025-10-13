package com.example.game.unit.service;

import com.example.game.common.exception.ErrorCode;
import com.example.game.common.exception.GlobalException;
import com.example.game.unit.dto.UnitDeployRequestDto;
import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.entity.UserGameInfo;
import com.example.game.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UnitServiceDeployUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;

    @DisplayName("정상적으로 유닛을 배치한다.")
    @Test
    void deployUnitTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));

        // when
        unitService.deployUnit(user, new UnitDeployRequestDto(unit.getUnitId(), Deploy.A1.toString()));

        // then
        unit = unitRepository.findById(unit.getUnitId())
                .get();
        Assertions.assertThat(unit.getDeploy()).isEqualTo(Deploy.A1);
    }

    @DisplayName("중복되는 위치에 배치를 시도할 경우 실패한다.")
    @Test
    void deployUnitDuplicateTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));
        Unit unit2 = unitRepository.save(new Unit(user));
        unitService.deployUnit(user, new UnitDeployRequestDto(unit.getUnitId(), Deploy.A1.toString()));

        // when then
        Assertions.assertThatThrownBy(() ->
                        unitService.deployUnit(user, new UnitDeployRequestDto(unit2.getUnitId(), Deploy.A1.toString())))
                .isInstanceOf(GlobalException.class)
                .hasMessage(ErrorCode.DESTINATION_NOT_EMPTY.getMessage());
    }

    @DisplayName("정상적으로 배치를 해제 한다. (null로 배치)")
    @Test
    void unitDeployNullTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user));

        // when
        unitService.deployUnit(user, new UnitDeployRequestDto(unit.getUnitId(), null));

        // then
        unit = unitRepository.findById(unit.getUnitId())
                .get();
        Assertions.assertThat(unit.getDeploy()).isNull();
    }

    @DisplayName("자신의 유닛이 아닌 경우 예외가 발생한다.")
    @Test
    void notMyUnitDeployFailTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));
        User user2 = userRepository.save(new User("testUser2", 2L, "testUser2", "testUser2", new UserGameInfo(500)));
        Unit unit = unitRepository.save(new Unit(user2));

        // when then
        Assertions.assertThatThrownBy(() ->
                        unitService.deployUnit(user, new UnitDeployRequestDto(unit.getUnitId(), Deploy.A1.toString())))
                .isInstanceOf(GlobalException.class)
                .hasMessage(ErrorCode.VALIDATION_FAIL.getMessage());

    }

}
