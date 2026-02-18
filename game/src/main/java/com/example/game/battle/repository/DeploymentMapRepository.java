package com.example.game.battle.repository;

import com.example.game.battle.entity.DeploymentMap;
import com.example.game.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeploymentMapRepository extends JpaRepository<DeploymentMap, Long> {
}
