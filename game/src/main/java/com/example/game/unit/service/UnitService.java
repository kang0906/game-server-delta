package com.example.game.unit.service;

import com.example.game.common.dto.ResponseDto;
import com.example.game.common.exception.GlobalException;
import com.example.game.unit.dto.UnitDeployRequestDto;
import com.example.game.unit.dto.UnitDto;
import com.example.game.unit.dto.UnitListResponseDto;
import com.example.game.unit.dto.UnitUpgradeResponseDto;
import com.example.game.unit.entity.Unit;
import com.example.game.unit.repository.UnitRepository;
import com.example.game.user.entity.User;
import com.example.game.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.game.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UnitService {

    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addUnit(User user) {

        user = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new GlobalException(DATA_NOT_FOUND));

        user.getUserGameInfo().useMoney(500);   // todo : 유닛 구매 금액은 외부에서 주입받아서 사용하도록 수정

        unitRepository.save(new Unit(user));
    }

    public UnitListResponseDto getUnitList(User user) {
        List<Unit> allByUser = unitRepository.findAllByUser(user);

        return new UnitListResponseDto(allByUser.stream().map(UnitDto::new).toList());
    }

    @Transactional
    public void deployUnit(User user, UnitDeployRequestDto unitDeployRequestDto) {

        if(unitDeployRequestDto.getDeployEnum() != null && unitRepository.findByUserAndDeploy(user, unitDeployRequestDto.getDeployEnum()).isPresent()){
            throw new GlobalException(DESTINATION_NOT_EMPTY);
        }

        user = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new GlobalException(DATA_NOT_FOUND));
        Unit unit = unitRepository.findById(unitDeployRequestDto.getUnitId())
                .orElseThrow(() -> new GlobalException(DATA_NOT_FOUND));

        if(unit.getUser().getUserId() != user.getUserId()) {
            throw new GlobalException(VALIDATION_FAIL);
        }

        unit.deployUnit(unitDeployRequestDto.getDeployEnum());
    }

    public UnitUpgradeResponseDto getUnitUpgradeList(User user, Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new GlobalException(DATA_NOT_FOUND));
        if(unit.getUser().getUserId() != user.getUserId()) {
            throw new GlobalException(VALIDATION_FAIL);
        }

        if(unit.getUpgradeList() == null){
            throw new GlobalException(CAN_NOT_UPGRADE);
        }

        return new UnitUpgradeResponseDto(unit.getUpgradeList());
    }
}
