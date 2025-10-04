package com.example.game.unit.dto;

import com.example.game.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitListResponseDto {
    private List<UnitDto> unitList;
}
