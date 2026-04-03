package ariane5.modules.obc;

import ariane5.core.EventBus;
import ariane5.domain.enums.TipoTransmissao;
import ariane5.domain.model.AtuadorMotor;
import ariane5.domain.model.AutoDestruicao;
import ariane5.domain.model.ComandoTrajetoria;
import ariane5.domain.model.DadosDiagnostico;
import ariane5.domain.model.LimiteSeguranca;
import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.TransmissaoSolo;
import ariane5.interfaces.TransmissionChannel;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class OBC {
    private final EventBus eventBus;
    private final AtuadorMotor atuadorMotor;
    private final TransmissionChannel transmissionChannel;
    private final LimiteSeguranca limiteAceleracao;
    private PacoteNavegacao ultimoPacoteValido;
    private boolean modoSeguro;

    public OBC(
            EventBus eventBus,
            AtuadorMotor atuadorMotor,
            TransmissionChannel transmissionChannel,
            LimiteSeguranca limiteAceleracao
    ) {
        this.eventBus = eventBus;
        this.atuadorMotor = atuadorMotor;
        this.transmissionChannel = transmissionChannel;
        this.limiteAceleracao = limiteAceleracao;
    }

    public PacoteNavegacao processarEntradas(List<PacoteNavegacao> pacotes, List<DadosDiagnostico> diagnosticos) {
        PacoteNavegacao pacoteSelecionado = pacotes.stream()
                .filter(pacote -> pacote != null && pacote.isValido())
                .max(Comparator.comparing(PacoteNavegacao::getTimestamp))
                .orElse(null);

        if (pacoteSelecionado == null) {
            modoSeguro = true;
            DadosDiagnostico diagnostico = new DadosDiagnostico(
                    LocalDateTime.now(),
                    "OBC",
                    "nenhum pacote valido recebido"
            );
            diagnosticos.add(diagnostico);
            transmitirDiagnostico(diagnostico);
            return null;
        }

        ultimoPacoteValido = pacoteSelecionado;
        transmitirDiagnostico(new DadosDiagnostico(LocalDateTime.now(), "OBC", "pacote selecionado=" + pacoteSelecionado.getCoordenadas()));
        return pacoteSelecionado;
    }

    public boolean validarDadosNavegacao(PacoteNavegacao pacote) {
        return pacote != null && pacote.isValido() && pacote.getCoordenadas() != null;
    }

    public ComandoTrajetoria emitirComandoTrajetoria(PacoteNavegacao pacote) {
        ComandoTrajetoria comando = new ComandoTrajetoria(
                LocalDateTime.now(),
                "CORRECAO_FINA",
                0.42,
                atuadorMotor
        );
        comando.executar();
        return comando;
    }

    public AutoDestruicao acionarAutoDestruicao(String motivo) {
        AutoDestruicao autoDestruicao = new AutoDestruicao(LocalDateTime.now(), motivo);
        autoDestruicao.armar();
        autoDestruicao.disparar();
        return autoDestruicao;
    }

    public boolean validarLimitesAceleracao(double valor) {
        return limiteAceleracao.estaDentroDosLimites(valor);
    }

    private void transmitirDiagnostico(DadosDiagnostico diagnostico) {
        TransmissaoSolo transmissao = new TransmissaoSolo(
                LocalDateTime.now(),
                TipoTransmissao.DIAGNOSTICO,
                diagnostico.serializar()
        );
        diagnostico.marcarComoEnviado();
        transmissionChannel.transmitir(transmissao);
    }

    public boolean isModoSeguro() {
        return modoSeguro;
    }

    public PacoteNavegacao getUltimoPacoteValido() {
        return ultimoPacoteValido;
    }
}
