package com.example.game.battle.service;

import com.example.game.battle.dto.report.BattleReport;
import com.example.game.battle.entity.DeploymentMap;
import com.example.game.battle.repository.DeploymentMapRepository;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.entity.UserGameInfo;
import com.example.game.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BattleServiceTest {

    @Autowired
    private BattleService battleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private DeploymentMapRepository deploymentMapRepository;

    @DisplayName("전투 시뮬레이션 테스트")
    @Test
    void battleTest() {
        // given
        User user = userRepository.save(new User("testUser1", 1L, "testUser1", "testUser1", new UserGameInfo(500)));
        User user2 = userRepository.save(new User("testUser2", 2L, "testUser2", "testUser2", new UserGameInfo(500)));

        List<Unit> unitList = new ArrayList<>();
        unitList.add(new Unit(user2).deployUnit(Deploy.C1));
        unitList.add(new Unit(user2).deployUnit(Deploy.D1));
        unitList.add(new Unit(user2).deployUnit(Deploy.E1));
        unitList.add(new Unit(user2).deployUnit(Deploy.A1));
        unitList.add(new Unit(user2).deployUnit(Deploy.B1));
        deploymentMapRepository.save(new DeploymentMap(unitList));

        unitRepository.save(new Unit(user).deployUnit(Deploy.C2));
        unitRepository.save(new Unit(user).deployUnit(Deploy.D2));
        unitRepository.save(new Unit(user).deployUnit(Deploy.E2));

        // when
        BattleReport battleReport = battleService.makeBattle(user);

        // then
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 필수 등록
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            System.out.println("battleReport = \n" + objectMapper.writeValueAsString(battleReport));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


}
