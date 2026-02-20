package com.example.game.battle.repository;

import com.example.game.battle.entity.DeploymentMap;
import com.example.game.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeploymentMapRepository extends JpaRepository<DeploymentMap, Long> {

    Optional<DeploymentMap> findFirstByMmrGreaterThanEqualOrderByMmrAsc(int mmr);
    Optional<DeploymentMap> findFirstByMmrLessThanEqualOrderByMmrDesc(int mmr);
    Optional<DeploymentMap> findByMmr(int mmr);

}
