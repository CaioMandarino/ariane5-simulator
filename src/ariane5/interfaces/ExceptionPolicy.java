package ariane5.interfaces;

import ariane5.domain.enums.TipoExcecao;
import ariane5.domain.event.FailureDetectedEvent;
import ariane5.domain.model.DadosDiagnostico;
import ariane5.domain.model.EventoFalha;

public interface ExceptionPolicy {
    DadosDiagnostico handle(String origem, TipoExcecao tipoExcecao, String causa, FailureDetectedEvent event);

    EventoFalha createFailure(String origem, TipoExcecao tipoExcecao, String causa);
}
