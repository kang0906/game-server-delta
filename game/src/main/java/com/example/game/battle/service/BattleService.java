package com.example.game.battle.service;

import com.example.game.battle.dto.BattleUnit;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BattleService {
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final DeploymentMapService deploymentMapService;

    public void makeBattle(User user) {    // 트랜잭션 사용 X : 알고리즘 연산 시 커넥션을 너무 오래 물고있을수 있음
        // 유저 유닛 배치도 저장
        deploymentMapService.saveDeploymentMap(user);

        // 유닛 데이터 초기화 (스탯 연산, 세팅)
        int roundCounter = 0;

        List<Unit> userUnit = unitRepository.findAllByUserAndDeployIsNotNull(user);

        List<BattleUnit> userUnitList = userUnit.stream().map(BattleUnit::new).toList();
        List<BattleUnit> hostileUnitList = deploymentMapService.findDeploymentMapByMMR(userUnit.stream().mapToInt(Unit::getLevel).sum());


        // 전투 진행

        // 결과 생성, 반환
    }
}
