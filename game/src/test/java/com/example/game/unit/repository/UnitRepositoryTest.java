package com.example.game.unit.repository;

import com.example.game.unit.dto.UnitListResponseDto;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.entity.enums.UnitType;
import com.example.game.user.entity.User;
import com.example.game.user.entity.UserGameInfo;
import com.example.game.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UnitRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @DisplayName("유저의 배치된 유닛을 모두 조회한다.")
    @Test
    void findAllByUserAndDeployIsNotNullTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));

        Unit unit = unitRepository.save(new Unit(user));
        Unit unit2 = unitRepository.save(new Unit(user));
        unit2.deployUnit(Deploy.A1);
        Unit unit3 = unitRepository.save(new Unit(user));
        unit3.deployUnit(Deploy.A2);

        // when
        List<Unit> unitList = unitRepository.findAllByUserAndDeployIsNotNull(user);

        // then
        Assertions.assertThat(unitList)
                .hasSize(2)
                .extracting("deploy")
                .isNotNull();
    }

}
