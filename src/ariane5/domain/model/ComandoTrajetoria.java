package ariane5.domain.model;

import ariane5.domain.command.FlightCommand;

import java.time.LocalDateTime;

public class ComandoTrajetoria implements FlightCommand {
    private final LocalDateTime timestamp;
    private final String tipoCorrecao;
    private final double intensidade;
    private final AtuadorMotor atuador;

    public ComandoTrajetoria(LocalDateTime timestamp, String tipoCorrecao, double intensidade, AtuadorMotor atuador) {
        this.timestamp = timestamp;
        this.tipoCorrecao = tipoCorrecao;
        this.intensidade = intensidade;
        this.atuador = atuador;
    }

    @Override
    public void executar() {
        atuador.aplicarComando(this);
    }

    public String resumo() {
        return "ComandoTrajetoria{" +
                "timestamp=" + timestamp +
                ", tipoCorrecao='" + tipoCorrecao + '\'' +
                ", intensidade=" + intensidade +
                '}';
    }
}
