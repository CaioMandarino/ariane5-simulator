package ariane5.domain.model;

import java.time.LocalDateTime;

public class DadosDiagnostico {
    private final LocalDateTime timestamp;
    private final String origem;
    private final String evento;
    private boolean enviado;

    public DadosDiagnostico(LocalDateTime timestamp, String origem, String evento) {
        this.timestamp = timestamp;
        this.origem = origem;
        this.evento = evento;
    }

    public void marcarComoEnviado() {
        this.enviado = true;
    }

    public String serializar() {
        return timestamp + "|" + origem + "|" + evento + "|enviado=" + enviado;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getOrigem() {
        return origem;
    }

    public String getEvento() {
        return evento;
    }
}
