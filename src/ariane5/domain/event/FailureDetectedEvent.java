package ariane5.domain.event;

import ariane5.core.DomainEvent;
import ariane5.domain.model.EventoFalha;

import java.time.LocalDateTime;

public class FailureDetectedEvent implements DomainEvent {
    private final EventoFalha eventoFalha;

    public FailureDetectedEvent(EventoFalha eventoFalha) {
        this.eventoFalha = eventoFalha;
    }

    public EventoFalha getEventoFalha() {
        return eventoFalha;
    }

    @Override
    public LocalDateTime occurredAt() {
        return eventoFalha.getTimestamp();
    }

    @Override
    public String description() {
        return "Falha detectada: " + eventoFalha.classificar();
    }
}
