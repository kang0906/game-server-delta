package com.example.game.battle.service;

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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DeploymentMapService {

    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final DeploymentMapRepository deploymentMapRepository;

    @Transactional
    public void saveDeploymentMap(User user) {
        List<Unit> unitList = unitRepository.findAllByUserAndDeployIsNotNull(user);

        deploymentMapRepository.save(new DeploymentMap(unitList));

    }
}
