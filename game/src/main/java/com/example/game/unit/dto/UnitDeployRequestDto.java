package com.example.game.unit.dto;

import com.example.game.unit.entity.enums.Deploy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDeployRequestDto {
    private Long unitId;
    private String deploy;

    public Deploy getDeployEnum() {
        if(deploy == null) {
            return null;
        }
        return Deploy.valueOf(deploy);
    }
}
