package config.bootstrap;


import config.bootstrapper.TestDataConstants;
import config.controller.RiskMatrixController;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumn;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.Attributes.Weight;
import eapli.framework.actions.Action;


/**
 *
 * @author João Flores
 */
@SuppressWarnings("squid:S106")
public class RiskFactorWeightBootstrap  implements Action {
    
    private DefinedColumnBuilder builder;
    
    public RiskFactorWeightBootstrap(DefinedColumnBuilder builder) {
        this.builder = builder;
    }

    @Override
    public boolean execute() {
        try {
            register(TestDataConstants.WEIGHT1);
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }


    private void register(Weight weigth) {
        builder.setWeight(weigth);
    }


}
