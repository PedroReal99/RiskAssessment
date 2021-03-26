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
public class WeightBootstrap  implements Action {
    
    private ColumnBuilder [] builders;
    
    public WeightBootstrap(ColumnBuilder [] builders) {
        this.builders = builders;
    }

    @Override
    public boolean execute() {
        try {
            register(TestDataConstants.WEIGHT1,0);
            register(TestDataConstants.WEIGHT2,1);
            register(TestDataConstants.WEIGHT3,2);
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }


    private void register(Weight weigth,int index) {
        ((DefinedColumnBuilder)builders[index]).setWeight(weigth);
    }


}
