package com.galleria.bank.dto.health;

import lombok.Data;
import lombok.Setter;


@Setter
@Data
public class HealthStatusDTO {
    private String statusDB;

    public HealthStatusDTO(String status) {
        this.statusDB = status;
    }

}
