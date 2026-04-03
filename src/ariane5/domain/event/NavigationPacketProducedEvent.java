package ariane5.domain.event;

import ariane5.core.DomainEvent;
import ariane5.domain.model.PacoteNavegacao;

import java.time.LocalDateTime;

public class NavigationPacketProducedEvent implements DomainEvent {
    private final String origem;
    private final PacoteNavegacao pacote;

    public NavigationPacketProducedEvent(String origem, PacoteNavegacao pacote) {
        this.origem = origem;
        this.pacote = pacote;
    }

    public String getOrigem() {
        return origem;
    }

    public PacoteNavegacao getPacote() {
        return pacote;
    }

    @Override
    public LocalDateTime occurredAt() {
        return pacote.getTimestamp();
    }

    @Override
    public String description() {
        return "Pacote de navegacao produzido por " + origem + ": " + pacote;
    }
}
