package com.galleria.bank.service.health;

import com.galleria.bank.dto.health.HealthStatusDTO;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class HealthService {

    private final DataSource dataSource;

    public HealthService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public HealthStatusDTO getHealthStatus() {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(2)
                    ? new HealthStatusDTO("UP")
                    : new HealthStatusDTO("DOWN");
        } catch (Exception e) {
            return new HealthStatusDTO("DOWN");
        }
    }
}

