package com.example.game.unit.service;

import com.example.game.common.exception.ErrorCode;
import com.example.game.common.exception.GlobalException;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.entity.UserGameInfo;
import com.example.game.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.groups.Tuple.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UnitServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;
    
    @DisplayName("유닛을 정상적으로 추가한다.")
    @Test
    void unitAddTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(500)));

        // when
        unitService.addUnit(user);
        
        // then
        Assertions.assertThat(user.getUserGameInfo().getMoney())
                .isEqualTo(0);
        Assertions.assertThat(unitRepository.findAllByUser(user))
                .hasSize(1)
                .extracting("user", "deploy")
                .containsExactlyInAnyOrder(
                        tuple(user, null)
                );
        
    }
    
    @DisplayName("잔액이 부족할 경우 유닛 추가에 실패한다.")
    @Test
    void unitAddNotEnoughMoneyExceptionTest() {
        // given
        User user = userRepository.save(new User("testUser", 1L, "testUser", "testUser", new UserGameInfo(499)));

        // when then
        Assertions.assertThatThrownBy(() ->
                        unitService.addUnit(user))
                .isInstanceOf(GlobalException.class)
                .hasMessage(ErrorCode.NOT_ENOUGH_MONEY.getMessage());
    }
    
    

}
