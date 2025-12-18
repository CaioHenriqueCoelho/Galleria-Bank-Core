package com.galleria.bank.controller;

import com.galleria.bank.dto.health.HealthStatusDTO;
import com.galleria.bank.service.health.HealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<HealthStatusDTO> login() {
        return ResponseEntity.ok(healthService.getHealthStatus());
    }
}
