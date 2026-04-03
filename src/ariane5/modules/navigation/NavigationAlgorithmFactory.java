package ariane5.modules.navigation;

import ariane5.domain.enums.ModeloParametros;
import ariane5.interfaces.NavigationAlgorithm;

public final class NavigationAlgorithmFactory {
    private NavigationAlgorithmFactory() {
    }

    public static NavigationAlgorithm create(ModeloParametros modelo) {
        return switch (modelo) {
            case ARIANE_4 -> new Ariane4NavigationStrategy();
            case ARIANE_5 -> new Ariane5NavigationStrategy();
        };
    }
}
