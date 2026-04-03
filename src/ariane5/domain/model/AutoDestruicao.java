package ariane5.domain.model;

import java.time.LocalDateTime;

public class AutoDestruicao {
    private final LocalDateTime timestamp;
    private final String motivo;
    private boolean armado;

    public AutoDestruicao(LocalDateTime timestamp, String motivo) {
        this.timestamp = timestamp;
        this.motivo = motivo;
    }

    public void armar() {
        armado = true;
        System.out.println("[SEGURANCA] Sistema de autodestruicao armado por: " + motivo);
    }

    public void disparar() {
        if (armado) {
            System.out.println("[SEGURANCA] Autodestruicao disparada em " + timestamp);
        }
    }
}
