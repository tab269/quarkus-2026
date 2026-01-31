package org.acme.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import java.time.LocalDateTime;

import static org.eclipse.microprofile.health.HealthCheckResponse.down;
import static org.eclipse.microprofile.health.HealthCheckResponse.up;

@Liveness
@ApplicationScoped
public class SimpleHealthCheck implements HealthCheck {

    public static final String SIMPLE_HEALTH_CHECK = "Simple health check";

    /** simuliert eine Healthiness
     * In Minuten, die gerade sind up, sonst down
     * @return die HealthCheckResponse (up oder down)
     */
    @Override
    public HealthCheckResponse call() {
        if (LocalDateTime.now().getMinute() % 2 == 0) {
            return up(SIMPLE_HEALTH_CHECK);
        }
        return down(SIMPLE_HEALTH_CHECK);
    }
}