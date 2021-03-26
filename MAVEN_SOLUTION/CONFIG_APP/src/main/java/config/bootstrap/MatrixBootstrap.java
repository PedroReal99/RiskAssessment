package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import core.persistence.MatrixRepository;
import core.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class MatrixBootstrap implements Action {

    ColumnBuilder[] builders;
    MatrixBuilder mb;

    public MatrixBootstrap(MatrixBuilder matrixB,ColumnBuilder[] builders) {
        this.mb=matrixB;
        this.builders = builders;
    }

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
        if (builders[0].validate()&&builders[1].validate()&&builders[2].validate()) {
            mb.addColumnBuilder(TestDataConstants.COV1, builders[0]);
            mb.addColumnBuilder(TestDataConstants.COV1, builders[1]);
            mb.addColumnBuilder(TestDataConstants.COV2, builders[1]);
            mb.addColumnBuilder(TestDataConstants.COV2, builders[2]);
            mb.addColumnBuilder(TestDataConstants.COV3, builders[2]);
        }
        try {
            mb.fillByDefault();
            MatrixRepository r = PersistenceContext.repositories().riskMatrixRepository();
            r.save(mb.build());
        } catch (Exception ex) {
            System.out.println("Error: Something went wrong when persisting this Base Matrix type!\n");
        }

    }
}
