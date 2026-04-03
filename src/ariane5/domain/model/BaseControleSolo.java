package ariane5.domain.model;

public class BaseControleSolo {
    private final String nome;

    public BaseControleSolo(String nome) {
        this.nome = nome;
    }

    public void receber(TransmissaoSolo transmissao) {
        System.out.println("[SOLO] " + nome + " recebeu: " + transmissao.serializar());
    }
}
