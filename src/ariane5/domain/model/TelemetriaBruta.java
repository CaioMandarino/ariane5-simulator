package ariane5.domain.model;

import java.time.LocalDateTime;

public class TelemetriaBruta {
    private final LocalDateTime timestamp;
    private final String dadosFisicos;
    private final double aceleracao;

    public TelemetriaBruta(LocalDateTime timestamp, String dadosFisicos, double aceleracao) {
        this.timestamp = timestamp;
        this.dadosFisicos = dadosFisicos;
        this.aceleracao = aceleracao;
    }

    public boolean validarIntegridade() {
        return dadosFisicos != null && !dadosFisicos.isBlank() && !Double.isNaN(aceleracao);
    }

    public TelemetriaBruta normalizar() {
        double aceleracaoNormalizada = Math.max(-500.0, Math.min(500.0, aceleracao));
        return new TelemetriaBruta(timestamp, dadosFisicos.trim(), aceleracaoNormalizada);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDadosFisicos() {
        return dadosFisicos;
    }

    public double getAceleracao() {
        return aceleracao;
    }

    @Override
    public String toString() {
        return "TelemetriaBruta{" +
                "timestamp=" + timestamp +
                ", dadosFisicos='" + dadosFisicos + '\'' +
                ", aceleracao=" + aceleracao +
                '}';
    }
}
