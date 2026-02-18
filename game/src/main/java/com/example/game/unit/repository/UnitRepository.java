package com.example.game.unit.repository;

import com.example.game.unit.entity.enums.Deploy;
import com.example.game.unit.entity.Unit;
import com.example.game.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    public List<Unit> findAllByUser(User user);
    public Optional<Unit> findByUserAndDeploy(User user, Deploy deploy);
    public List<Unit> findAllByUserAndDeployIsNotNull(User user);
}
