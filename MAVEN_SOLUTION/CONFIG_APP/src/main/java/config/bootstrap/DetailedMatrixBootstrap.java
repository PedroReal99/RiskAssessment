package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import config.controller.RiskMatrixController;
import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.Columns.DetailedColumn;
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import core.domain.RiskMatrixs.Line;
import core.domain.RiskMatrixs.MatrixBuilder;
import core.domain.RiskMatrixs.MatrixState;
import eapli.framework.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class DetailedMatrixBootstrap implements Action {
    @Override
    public boolean execute() {
        try {
            registerDetailedMatrix();
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }

    public void registerDetailedMatrix() {
        DetailedColumnBuilder cb = (DetailedColumnBuilder) DetailedColumnBuilder.newColumnBuilder(DetailedColumnBuilder.State.DEFINED);
        
        cb.setRiskFactor(TestDataConstants.RSK1);
        cb.setContribution(TestDataConstants.CONT1);
        cb.setNeed(TestDataConstants.NEED1);
        cb.setScale(TestDataConstants.SCL1);
        cb.setWeight(TestDataConstants.WEIGHT1);
        
        DetailedColumnBuilder cb1 = (DetailedColumnBuilder) DetailedColumnBuilder.newColumnBuilder(DetailedColumnBuilder.State.DEFINED);
        
        cb1.setRiskFactor(TestDataConstants.RSK2);
        cb1.setContribution(TestDataConstants.CONT2);
        cb1.setNeed(TestDataConstants.NEED2);
        cb1.setScale(TestDataConstants.SCL2);
        cb1.setWeight(TestDataConstants.WEIGHT2);

        DetailedColumnBuilder cb2 = (DetailedColumnBuilder) DetailedColumnBuilder.newColumnBuilder(DetailedColumnBuilder.State.DEFINED);
        
        cb2.setRiskFactor(TestDataConstants.RSK3);
        cb2.setContribution(TestDataConstants.CONT3);
        cb2.setNeed(TestDataConstants.NEED3);
        cb2.setScale(TestDataConstants.SCL3);
        cb2.setWeight(TestDataConstants.WEIGHT3);
        
        if (cb.validate() && cb1.validate() && cb2.validate()) {
            MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DETAILED);
            mb.addColumnBuilder(TestDataConstants.COV1, cb);
            TestDataConstants.COV1.associateRisk(cb.getRisk());
            mb.addColumnBuilder(TestDataConstants.COV2, cb1);
            TestDataConstants.COV2.associateRisk(cb1.getRisk());
            mb.addColumnBuilder(TestDataConstants.COV3, cb2);
            TestDataConstants.COV3.associateRisk(cb2.getRisk());
        }
    }
}
