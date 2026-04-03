package ariane5.domain.event;

import ariane5.core.DomainEvent;
import ariane5.domain.model.TelemetriaBruta;

import java.time.LocalDateTime;

public class TelemetryGeneratedEvent implements DomainEvent {
    private final TelemetriaBruta telemetria;

    public TelemetryGeneratedEvent(TelemetriaBruta telemetria) {
        this.telemetria = telemetria;
    }

    public TelemetriaBruta getTelemetria() {
        return telemetria;
    }

    @Override
    public LocalDateTime occurredAt() {
        return telemetria.getTimestamp();
    }

    @Override
    public String description() {
        return "Telemetria gerada: " + telemetria;
    }
}
