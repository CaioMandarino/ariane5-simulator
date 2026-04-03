package ariane5.domain.model;

import ariane5.interfaces.Sensor;

import java.time.LocalDateTime;

public class SensorVoo implements Sensor {
    private final String tipo;
    private final String localizacao;
    private final double ultimaAceleracao;
    private final LocalDateTime ultimaLeitura;

    public SensorVoo(String tipo, String localizacao, double ultimaAceleracao, LocalDateTime ultimaLeitura) {
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.ultimaAceleracao = ultimaAceleracao;
        this.ultimaLeitura = ultimaLeitura;
    }

    public double lerAceleracao() {
        return ultimaAceleracao;
    }

    public String lerDadosFisicos() {
        return tipo + "@" + localizacao;
    }

    @Override
    public TelemetriaBruta gerarTelemetria() {
        return new TelemetriaBruta(ultimaLeitura, lerDadosFisicos(), lerAceleracao());
    }
}
