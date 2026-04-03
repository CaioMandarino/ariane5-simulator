package ariane5.modules.telemetry;

import ariane5.core.EventBus;
import ariane5.domain.event.TelemetryGeneratedEvent;
import ariane5.domain.model.TelemetriaBruta;
import ariane5.interfaces.Sensor;

import java.util.List;

public class TelemetryAggregator {
    private final List<Sensor> sensores;
    private final EventBus eventBus;

    public TelemetryAggregator(List<Sensor> sensores, EventBus eventBus) {
        this.sensores = sensores;
        this.eventBus = eventBus;
    }

    public TelemetriaBruta coletarTelemetriaPrincipal() {
        TelemetriaBruta telemetria = sensores.getFirst().gerarTelemetria();
        TelemetriaBruta normalizada = telemetria.normalizar();
        eventBus.publish(new TelemetryGeneratedEvent(normalizada));
        return normalizada;
    }
}
