package ariane5.modules.navigation;

import ariane5.domain.enums.TipoExcecao;

public class NavigationException extends RuntimeException {
    private final TipoExcecao tipoExcecao;

    public NavigationException(String message, TipoExcecao tipoExcecao) {
        super(message);
        this.tipoExcecao = tipoExcecao;
    }

    public TipoExcecao getTipoExcecao() {
        return tipoExcecao;
    }
}
