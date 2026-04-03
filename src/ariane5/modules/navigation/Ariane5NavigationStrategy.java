package ariane5.modules.navigation;

import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.TelemetriaBruta;
import ariane5.interfaces.NavigationAlgorithm;

public class Ariane5NavigationStrategy implements NavigationAlgorithm {
    @Override
    public PacoteNavegacao calcular(TelemetriaBruta telemetria) {
        double aceleracao = telemetria.getAceleracao();
        String coordenadas = "A5-LAT:" + (aceleracao * 0.55)
                + ";A5-VEL:" + (aceleracao * 1.9)
                + ";A5-ATT:" + (aceleracao * 0.07);
        return new PacoteNavegacao(telemetria.getTimestamp(), coordenadas, true);
    }
}
