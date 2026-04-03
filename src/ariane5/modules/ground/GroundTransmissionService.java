package ariane5.modules.ground;

import ariane5.domain.model.BaseControleSolo;
import ariane5.domain.model.TransmissaoSolo;
import ariane5.interfaces.TransmissionChannel;

public class GroundTransmissionService implements TransmissionChannel {
    private final BaseControleSolo baseControleSolo;

    public GroundTransmissionService(BaseControleSolo baseControleSolo) {
        this.baseControleSolo = baseControleSolo;
    }

    @Override
    public void transmitir(TransmissaoSolo transmissao) {
        baseControleSolo.receber(transmissao);
    }
}
