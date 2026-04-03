package ariane5.domain.model;

import ariane5.domain.enums.TipoTransmissao;

import java.time.LocalDateTime;

public class TransmissaoSolo {
    private final LocalDateTime timestamp;
    private final TipoTransmissao tipo;
    private final String payload;

    public TransmissaoSolo(LocalDateTime timestamp, TipoTransmissao tipo, String payload) {
        this.timestamp = timestamp;
        this.tipo = tipo;
        this.payload = payload;
    }

    public String serializar() {
        return timestamp + "|" + tipo + "|" + payload;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TipoTransmissao getTipo() {
        return tipo;
    }

    public String getPayload() {
        return payload;
    }
}
