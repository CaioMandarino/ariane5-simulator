package ariane5.domain.model;

import ariane5.domain.enums.TipoExcecao;

import java.time.LocalDateTime;

public class EventoFalha {
    private final LocalDateTime timestamp;
    private final String causa;
    private final TipoExcecao tipoExcecao;
    private final String origem;

    public EventoFalha(LocalDateTime timestamp, String causa, TipoExcecao tipoExcecao, String origem) {
        this.timestamp = timestamp;
        this.causa = causa;
        this.tipoExcecao = tipoExcecao;
        this.origem = origem;
    }

    public String classificar() {
        return origem + "-" + tipoExcecao.name();
    }

    public void emitirAlerta() {
        System.out.println("[ALERTA] Falha critica detectada: " + classificar() + " causa=" + causa);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCausa() {
        return causa;
    }

    public TipoExcecao getTipoExcecao() {
        return tipoExcecao;
    }

    public String getOrigem() {
        return origem;
    }
}
