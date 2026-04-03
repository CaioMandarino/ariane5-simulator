package ariane5.interfaces;

import ariane5.domain.model.TransmissaoSolo;

public interface TransmissionChannel {
    void transmitir(TransmissaoSolo transmissao);
}
