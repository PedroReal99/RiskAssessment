/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrapper;

import config.bootstrap.MatrixBootstrap;
import config.bootstrap.ContributionBootstrap;
import config.bootstrap.CoverageBootstrap;
import config.bootstrap.NeedOfAnalisysBootstrap;
import config.bootstrap.RiskFactorBootstrap;
import config.bootstrap.ScaleBootstrap;
import config.bootstrap.SurroundingBootstrap;
import config.bootstrap.SurroundingTypeBootstrap;
import config.bootstrap.UserBootstrap;
import config.bootstrap.WeightBootstrap;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import eapli.framework.actions.Action;
import eapli.framework.util.Strings;

/**
 *
 * @author Ricardo Branco
 */
public class InsuranceBootstrapper implements Action {

    @Override
    public boolean execute() {

        ColumnBuilder builders[] = {ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE)};

        boolean ret = true;

        // declare config.bootstrap actions
        Action[] actionsBase = {new UserBootstrap() , new SurroundingTypeBootstrap(), new SurroundingBootstrap(), new RiskFactorBootstrap(builders), new CoverageBootstrap(builders),
            new MatrixBootstrap(new MatrixBuilder(MatrixBuilder.State.BASE, TestDataConstants.BASE_VERSION), builders)};

        ret &= execute(actionsBase);

        for (int i = 0; i < 3; i++) {
            builders[i] = new DefinedColumnBuilder(builders[i]);
        }

        Action[] actionsDefined = {new WeightBootstrap(builders), new ContributionBootstrap(builders), new NeedOfAnalisysBootstrap(builders),
            new MatrixBootstrap(new MatrixBuilder(MatrixBuilder.State.DEFINED, TestDataConstants.DEFINED_VERSION), builders)};

        ret &= execute(actionsDefined);

        for (int i = 0; i < 3; i++) {
            builders[i] = new DetailedColumnBuilder(builders[i]);
        }

        Action[] actionsDetailed = {new ScaleBootstrap(builders),
            new MatrixBootstrap(new MatrixBuilder(MatrixBuilder.State.DETAILED, TestDataConstants.DETAILED_VERSION), builders)};

        ret &= execute(actionsDetailed);

        return ret;
    }

    private boolean execute(Action[] actions) {
        boolean ret = true;
        // execute all bootstrapping
        for (Action boot : actions) {
            System.out.println("Bootstrapping " + nameOfEntity(boot) + "...");
            ret &= boot.execute();
        }
        return ret;
    }

    private String nameOfEntity(final Action action) {
        final String name = action.getClass().getSimpleName();
        return Strings.left(name, name.length() - "Bootstrap".length());
    }

}
