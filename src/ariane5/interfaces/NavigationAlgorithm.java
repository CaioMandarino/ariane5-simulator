package ariane5.interfaces;

import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.TelemetriaBruta;

public interface NavigationAlgorithm {
    PacoteNavegacao calcular(TelemetriaBruta telemetria);
}
