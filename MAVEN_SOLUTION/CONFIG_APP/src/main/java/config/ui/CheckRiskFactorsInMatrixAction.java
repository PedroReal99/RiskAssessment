package config.ui;

import eapli.framework.actions.Action;

public class CheckRiskFactorsInMatrixAction implements Action {

    @Override
    public boolean execute() {
        return new CheckRiskFactorsInMatrixUI().doShow();
    }

}