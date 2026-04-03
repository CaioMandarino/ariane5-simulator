package ariane5.modules.navigation;

import ariane5.domain.enums.ModeloParametros;
import ariane5.domain.enums.TipoExcecao;
import ariane5.domain.model.PacoteNavegacao;
import ariane5.domain.model.TelemetriaBruta;
import ariane5.interfaces.NavigationAlgorithm;

public class SoftwareNavegacao {
    private final String nome;
    private final String versao;
    private final boolean suportaExcecaoMatematica;
    private NavigationAlgorithm algoritmo;
    private ModeloParametros modeloAtual;

    public SoftwareNavegacao(String nome, String versao, boolean suportaExcecaoMatematica, ModeloParametros modeloAtual) {
        this.nome = nome;
        this.versao = versao;
        this.suportaExcecaoMatematica = suportaExcecaoMatematica;
        this.modeloAtual = modeloAtual;
        this.algoritmo = NavigationAlgorithmFactory.create(modeloAtual);
    }

    public void carregarModeloParametros(ModeloParametros modeloAtual) {
        this.modeloAtual = modeloAtual;
        this.algoritmo = NavigationAlgorithmFactory.create(modeloAtual);
    }

    public PacoteNavegacao executarCiclo(TelemetriaBruta telemetria) {
        try {
            return algoritmo.calcular(telemetria);
        } catch (NavigationException exception) {
            tratarExcecaoMatematica(exception.getTipoExcecao());
            throw exception;
        }
    }

    public void tratarExcecaoMatematica(TipoExcecao tipoExcecao) {
        if (!suportaExcecaoMatematica) {
            throw new NavigationException(
                    "Software " + nome + " nao suporta recuperacao para " + tipoExcecao,
                    tipoExcecao
            );
        }
    }

    public String identificacao() {
        return nome + " v" + versao + " [" + modeloAtual + "]";
    }
}
