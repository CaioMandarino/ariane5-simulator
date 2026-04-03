package ariane5.modules.safety;

import ariane5.domain.enums.TipoExcecao;
import ariane5.domain.event.FailureDetectedEvent;
import ariane5.domain.model.DadosDiagnostico;
import ariane5.domain.model.EventoFalha;
import ariane5.interfaces.ExceptionPolicy;

import java.time.LocalDateTime;

public class StrictExceptionPolicy implements ExceptionPolicy {
    @Override
    public DadosDiagnostico handle(String origem, TipoExcecao tipoExcecao, String causa, FailureDetectedEvent event) {
        return new DadosDiagnostico(
                LocalDateTime.now(),
                origem,
                "falhaCritica=" + tipoExcecao + ";classificacao=" + event.getEventoFalha().classificar() + ";causa=" + causa
        );
    }

    @Override
    public EventoFalha createFailure(String origem, TipoExcecao tipoExcecao, String causa) {
        return new EventoFalha(LocalDateTime.now(), causa, tipoExcecao, origem);
    }
}
