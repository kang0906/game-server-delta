package com.example.game.battle.service;

import com.example.game.admin.alert.entity.AlertLevel;
import com.example.game.admin.alert.service.AlertService;
import com.example.game.battle.dto.BattleUnit;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BattleService {
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final DeploymentMapService deploymentMapService;
    private final AlertService alertService;

    public void makeBattle(User user) {    // 트랜잭션 사용 X : 알고리즘 연산 시 커넥션을 너무 오래 물고있을수 있음
        // 유저 유닛 배치도 저장
        deploymentMapService.saveDeploymentMap(user);

        // 유닛 데이터 초기화 (스탯 연산, 세팅)
        int roundCounter = 0;

        List<Unit> userUnit = unitRepository.findAllByUserAndDeployIsNotNull(user);

        List<BattleUnit> userUnitList = new ArrayList<>(userUnit.stream().map(BattleUnit::new).toList());
        List<BattleUnit> hostileUnitList = new ArrayList<>(deploymentMapService.findDeploymentMapByMMR(userUnit.stream().mapToInt(Unit::getLevel).sum()));

        // 전투 진행
        while(true){
            log.debug("================ round : {} ===================", roundCounter);
            //유닛 순회
            log.debug("user move");
            for (BattleUnit battleUnit : userUnitList) {
                // 행동력(이동,공격 속도) 체크
                battleUnit.action(hostileUnitList, userUnitList);
                // 전투 종료 체크
                if (userUnitList.isEmpty() || hostileUnitList.isEmpty()) {
                    break;
                }
            }
            log.debug("enemy move");
            for (BattleUnit battleUnit : hostileUnitList) {
                // 행동력(이동,공격 속도) 체크
                battleUnit.action(userUnitList, hostileUnitList);
                // 전투 종료 체크
                if (userUnitList.isEmpty() || hostileUnitList.isEmpty()) {
                    break;
                }
            }
            if (userUnitList.isEmpty() || hostileUnitList.isEmpty()) {
                break;
            }

            if(roundCounter > 1000) {
                // 경고 메세지 전송
                alertService.sendAlert(AlertLevel.WARN, "전투 중 1000 라운드 초과 발생");
            }
            roundCounter++;
        }

        // 결과 생성, 반환 데이터 준비

        // 결과 데이터 DB 반영(트랜잭션 사용)
    }
}
