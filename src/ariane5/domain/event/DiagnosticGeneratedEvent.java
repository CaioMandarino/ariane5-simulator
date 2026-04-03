package ariane5.domain.event;

import ariane5.core.DomainEvent;
import ariane5.domain.model.DadosDiagnostico;

import java.time.LocalDateTime;

public class DiagnosticGeneratedEvent implements DomainEvent {
    private final DadosDiagnostico diagnostico;

    public DiagnosticGeneratedEvent(DadosDiagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public DadosDiagnostico getDiagnostico() {
        return diagnostico;
    }

    @Override
    public LocalDateTime occurredAt() {
        return diagnostico.getTimestamp();
    }

    @Override
    public String description() {
        return "Diagnostico gerado: " + diagnostico.serializar();
    }
}
