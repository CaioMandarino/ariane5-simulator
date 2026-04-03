package ariane5.domain.model;

import java.time.LocalDateTime;

public class PacoteNavegacao {
    private final LocalDateTime timestamp;
    private final String coordenadas;
    private boolean valido;

    public PacoteNavegacao(LocalDateTime timestamp, String coordenadas, boolean valido) {
        this.timestamp = timestamp;
        this.coordenadas = coordenadas;
        this.valido = valido;
    }

    public void marcarInvalido(String motivo) {
        this.valido = false;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public boolean isValido() {
        return valido;
    }

    @Override
    public String toString() {
        return "PacoteNavegacao{" +
                "timestamp=" + timestamp +
                ", coordenadas='" + coordenadas + '\'' +
                ", valido=" + valido +
                '}';
    }
}
