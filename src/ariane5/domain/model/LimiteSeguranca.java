package ariane5.domain.model;

public class LimiteSeguranca {
    private final String parametro;
    private final double minimo;
    private final double maximo;

    public LimiteSeguranca(String parametro, double minimo, double maximo) {
        this.parametro = parametro;
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public boolean estaDentroDosLimites(double valor) {
        return valor >= minimo && valor <= maximo;
    }

    public String getParametro() {
        return parametro;
    }
}
