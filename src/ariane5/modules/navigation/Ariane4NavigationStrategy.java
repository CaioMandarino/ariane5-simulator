package ariane5.modules.navigation;

import ariane5.domain.enums.TipoExcecao;
import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.TelemetriaBruta;
import ariane5.interfaces.NavigationAlgorithm;

public class Ariane4NavigationStrategy implements NavigationAlgorithm {
    @Override
    public PacoteNavegacao calcular(TelemetriaBruta telemetria) {
        if (telemetria.getAceleracao() > 120.0) {
            throw new NavigationException(
                    "Conversao numerica excedeu o intervalo previsto no perfil Ariane 4",
                    TipoExcecao.OVERFLOW
            );
        }

        String coordenadas = "A4-LAT:" + (telemetria.getAceleracao() * 0.85)
                + ";A4-VEL:" + (telemetria.getAceleracao() * 1.2);
        return new PacoteNavegacao(telemetria.getTimestamp(), coordenadas, true);
    }
}
