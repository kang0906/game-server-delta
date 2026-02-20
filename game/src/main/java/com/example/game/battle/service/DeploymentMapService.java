package com.example.game.battle.service;

import com.example.game.admin.alert.entity.AlertLevel;
import com.example.game.admin.alert.service.AlertService;
import com.example.game.battle.dto.BattleUnit;
import com.example.game.battle.entity.DeployUnit;
import com.example.game.battle.entity.DeploymentMap;
import com.example.game.battle.repository.DeploymentMapRepository;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DeploymentMapService {

    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final DeploymentMapRepository deploymentMapRepository;
    private final AlertService alertService;

    @Transactional
    public void saveDeploymentMap(User user) {
        List<Unit> unitList = unitRepository.findAllByUserAndDeployIsNotNull(user);

        deploymentMapRepository.save(new DeploymentMap(unitList));
    }

    public List<BattleUnit> findDeploymentMapByMMR(int mmr) {
        Optional<DeploymentMap> exact = deploymentMapRepository.findByMmr(mmr);
        if (exact.isPresent()) {
            return exact.get().getDeployUnitList().stream().map(BattleUnit::new).toList();
        }

        Optional<DeploymentMap> upper =
                deploymentMapRepository.findFirstByMmrGreaterThanEqualOrderByMmrAsc(mmr);

        Optional<DeploymentMap> lower =
                deploymentMapRepository.findFirstByMmrLessThanEqualOrderByMmrDesc(mmr);

        if (upper.isEmpty() && lower.isEmpty()){
            alertService.sendAlert(AlertLevel.ERROR, "저장된 전투매핑정보가 없습니다.");
        }

        if (upper.isEmpty()) {
            return lower.get().getDeployUnitList().stream().map(BattleUnit::new).toList();
        } else if (lower.isEmpty()) {
            return upper.get().getDeployUnitList().stream().map(BattleUnit::new).toList();
        }

        int diffUpper = Math.abs(upper.get().getMmr() - mmr);
        int diffLower = Math.abs(lower.get().getMmr() - mmr);

        return (diffUpper < diffLower ? upper.get() : lower.get())
                    .getDeployUnitList().stream().map(BattleUnit::new).toList();
    }
}
