package ariane5.simulation;

import ariane5.bus.SimpleEventBus;
import ariane5.core.EventBus;
import ariane5.domain.enums.ModeloParametros;
import ariane5.domain.enums.SriModo;
import ariane5.domain.enums.TipoTransmissao;
import ariane5.domain.event.DiagnosticGeneratedEvent;
import ariane5.domain.event.FailureDetectedEvent;
import ariane5.domain.event.NavigationPacketProducedEvent;
import ariane5.domain.event.TelemetryGeneratedEvent;
import ariane5.domain.model.AtuadorMotor;
import ariane5.domain.model.BaseControleSolo;
import ariane5.domain.model.DadosDiagnostico;
import ariane5.domain.model.LimiteSeguranca;
import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.SensorVoo;
import ariane5.domain.model.TelemetriaBruta;
import ariane5.domain.model.TransmissaoSolo;
import ariane5.interfaces.Sensor;
import ariane5.modules.ground.GroundTransmissionService;
import ariane5.modules.obc.OBC;
import ariane5.modules.safety.SRI;
import ariane5.modules.safety.StrictExceptionPolicy;
import ariane5.modules.telemetry.TelemetryAggregator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ariane5LaunchSimulation {
    private final EventBus eventBus;
    private final TelemetryAggregator telemetryAggregator;
    private final SRI sriPrincipal;
    private final SRI sriBackup;
    private final OBC obc;
    private final List<DadosDiagnostico> diagnosticos = new ArrayList<>();

    public Ariane5LaunchSimulation() {
        this.eventBus = new SimpleEventBus();
        BaseControleSolo baseControleSolo = new BaseControleSolo("Kourou");
        GroundTransmissionService groundTransmissionService = new GroundTransmissionService(baseControleSolo);
        this.telemetryAggregator = new TelemetryAggregator(mockSensores(), eventBus);
        this.sriPrincipal = new SRI("SRI-1", SriModo.PRINCIPAL, ModeloParametros.ARIANE_4, new StrictExceptionPolicy(), eventBus);
        this.sriBackup = new SRI("SRI-2", SriModo.STANDBY, ModeloParametros.ARIANE_4, new StrictExceptionPolicy(), eventBus);
        this.obc = new OBC(
                eventBus,
                new AtuadorMotor("Bocal vetorial", "pitch"),
                groundTransmissionService,
                new LimiteSeguranca("aceleracao", -150.0, 150.0)
        );
        registrarAssinantes();
    }

    public void executar() {
        System.out.println("=== Simulacao Ariane 5 ===");
        TelemetriaBruta telemetria = telemetryAggregator.coletarTelemetriaPrincipal();

        if (!obc.validarLimitesAceleracao(telemetria.getAceleracao())) {
            diagnosticos.add(new DadosDiagnostico(LocalDateTime.now(), "OBC", "aceleracao fora do envelope nominal"));
        }

        PacoteNavegacao pacotePrincipal = sriPrincipal.calcularNavegacao(telemetria);
        PacoteNavegacao pacoteBackup = sriBackup.calcularNavegacao(telemetria);
        PacoteNavegacao pacoteSelecionado = obc.processarEntradas(
                Arrays.asList(pacotePrincipal, pacoteBackup),
                diagnosticos
        );

        if (obc.validarDadosNavegacao(pacoteSelecionado)) {
            obc.emitirComandoTrajetoria(pacoteSelecionado);
            TransmissaoSolo transmissao = new TransmissaoSolo(
                    LocalDateTime.now(),
                    TipoTransmissao.TELEMETRIA,
                    pacoteSelecionado.toString()
            );
            new GroundTransmissionService(new BaseControleSolo("Kourou")).transmitir(transmissao);
            return;
        }

        obc.acionarAutoDestruicao("Perda de navegacao redundante apos falha nos dois SRIs");
    }

    private List<Sensor> mockSensores() {
        return List.of(
                new SensorVoo("Inercial", "plataforma superior", 180.0, LocalDateTime.now())
        );
    }

    private void registrarAssinantes() {
        eventBus.subscribe(TelemetryGeneratedEvent.class, event ->
                System.out.println("[EVENTO] " + event.description()));

        eventBus.subscribe(NavigationPacketProducedEvent.class, event ->
                System.out.println("[EVENTO] " + event.description()));

        eventBus.subscribe(FailureDetectedEvent.class, event ->
                System.out.println("[EVENTO] " + event.description()));

        eventBus.subscribe(DiagnosticGeneratedEvent.class, event -> {
            diagnosticos.add(event.getDiagnostico());
            System.out.println("[EVENTO] " + event.description());
        });
    }
}
