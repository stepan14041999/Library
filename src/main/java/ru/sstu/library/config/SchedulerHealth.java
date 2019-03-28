package ru.sstu.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class SchedulerHealth implements HealthIndicator {
    @Value("${project.version}")
    private String version;

    @Override
    public Health health() {
        return Health.up().withDetail("version", version).build();
    }
}