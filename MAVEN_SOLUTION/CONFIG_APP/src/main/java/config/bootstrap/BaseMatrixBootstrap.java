package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import eapli.framework.actions.Action;

public class BaseMatrixBootstrap implements Action {

    @Override
    public boolean execute() {
        try {
            registerBaseMatrix();
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }

    public void registerBaseMatrix() {
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED);
        
        cb.setRiskFactor(TestDataConstants.RSK1);
        
        ColumnBuilder cb1 = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED);
        
        cb1.setRiskFactor(TestDataConstants.RSK2);

        if (cb.validate() && cb1.validate()) {
            MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.BASE);
            mb.addColumnBuilder(TestDataConstants.COV1, cb);
            TestDataConstants.COV1.associateRisk(cb.getRisk());
            mb.addColumnBuilder(TestDataConstants.COV2, cb1);
            TestDataConstants.COV2.associateRisk(cb1.getRisk());
        }

    }
}
