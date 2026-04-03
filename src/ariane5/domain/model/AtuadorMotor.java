package ariane5.domain.model;

public class AtuadorMotor {
    private final String tipo;
    private final String eixo;
    private ComandoTrajetoria ultimoComando;

    public AtuadorMotor(String tipo, String eixo) {
        this.tipo = tipo;
        this.eixo = eixo;
    }

    public void aplicarComando(ComandoTrajetoria comando) {
        this.ultimoComando = comando;
        System.out.println("[ATUADOR] " + tipo + "/" + eixo + " aplicando " + comando.resumo());
    }

    public ComandoTrajetoria getUltimoComando() {
        return ultimoComando;
    }
}
