package com.example.game.unit.controller;

import com.example.game.common.dto.ResponseDto;
import com.example.game.config.UserDetailsImpl;
import com.example.game.unit.dto.UnitDeployRequestDto;
import com.example.game.unit.dto.UnitListResponseDto;
import com.example.game.unit.dto.UnitUpgradeRequestDto;
import com.example.game.unit.dto.UnitUpgradeOptionsResponseDto;
import com.example.game.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping("/unit")
    public ResponseDto<String> addUnit(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        unitService.addUnit(userDetails.getUser());

        return ResponseDto.success("success");
    }

    @GetMapping("/unit")
    public ResponseDto<UnitListResponseDto> getUnitList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(unitService.getUnitList(userDetails.getUser()));
    }

    @PostMapping("/unit/deploy")
    public ResponseDto<String> deployUnit(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UnitDeployRequestDto unitDeployRequestDto) {
        unitService.deployUnit(userDetails.getUser(), unitDeployRequestDto);

        return ResponseDto.success("success");
    }

    @GetMapping("/unit/{unitId}/upgrade")
    public ResponseDto<UnitUpgradeOptionsResponseDto> getUnitUpgradeList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long unitId) {
        return ResponseDto.success(unitService.getUnitUpgradeList(userDetails.getUser(), unitId));
    }

    @PostMapping("/unit/{unitId}/upgrade")
    public ResponseDto<String> getUnitUpgradeList(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long unitId, @RequestBody UnitUpgradeRequestDto unitUpgradeRequestDto) {

        unitService.unitUpgrade(userDetails.getUser(), unitId, unitUpgradeRequestDto.getUpgradeOptionEnumByString());
        return ResponseDto.success("success");
    }
}
