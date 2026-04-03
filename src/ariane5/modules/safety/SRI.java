package ariane5.modules.safety;

import ariane5.core.EventBus;
import ariane5.domain.enums.ModeloParametros;
import ariane5.domain.enums.SriModo;
import ariane5.domain.event.DiagnosticGeneratedEvent;
import ariane5.domain.event.FailureDetectedEvent;
import ariane5.domain.event.NavigationPacketProducedEvent;
import ariane5.domain.model.DadosDiagnostico;
import ariane5.domain.model.EventoFalha;
import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.TelemetriaBruta;
import ariane5.interfaces.ExceptionPolicy;
import ariane5.modules.navigation.NavigationException;
import ariane5.modules.navigation.SoftwareNavegacao;

public class SRI {
    private final String nome;
    private final SriModo modo;
    private final SoftwareNavegacao softwareNavegacao;
    private final ExceptionPolicy exceptionPolicy;
    private final EventBus eventBus;
    private boolean ativo;
    private PacoteNavegacao ultimoPacote;

    public SRI(
            String nome,
            SriModo modo,
            ModeloParametros modeloParametros,
            ExceptionPolicy exceptionPolicy,
            EventBus eventBus
    ) {
        this.nome = nome;
        this.modo = modo;
        this.exceptionPolicy = exceptionPolicy;
        this.eventBus = eventBus;
        this.ativo = true;
        this.softwareNavegacao = new SoftwareNavegacao(nome + "-nav", "1.0.0", false, modeloParametros);
    }

    public PacoteNavegacao calcularNavegacao(TelemetriaBruta telemetria) {
        if (!ativo) {
            return null;
        }

        try {
            ultimoPacote = softwareNavegacao.executarCiclo(telemetria);
            eventBus.publish(new NavigationPacketProducedEvent(nome, ultimoPacote));
            return ultimoPacote;
        } catch (NavigationException exception) {
            ativo = false;
            EventoFalha falha = exceptionPolicy.createFailure(nome, exception.getTipoExcecao(), exception.getMessage());
            falha.emitirAlerta();
            FailureDetectedEvent failureEvent = new FailureDetectedEvent(falha);
            eventBus.publish(failureEvent);

            DadosDiagnostico diagnostico = exceptionPolicy.handle(nome, exception.getTipoExcecao(), exception.getMessage(), failureEvent);
            eventBus.publish(new DiagnosticGeneratedEvent(diagnostico));
            return null;
        }
    }

    public DadosDiagnostico gerarLogRecuperacao() {
        return new DadosDiagnostico(java.time.LocalDateTime.now(), nome, "estado=" + ativo + ";modo=" + modo);
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public PacoteNavegacao getUltimoPacote() {
        return ultimoPacote;
    }
}
