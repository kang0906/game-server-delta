package com.example.game.unit.repository;

import com.example.game.unit.entity.Unit;
import com.example.game.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    public List<Unit> findAllByUser(User user);

}
