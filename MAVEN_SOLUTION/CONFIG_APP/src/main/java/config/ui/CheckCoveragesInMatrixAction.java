package config.ui;

import eapli.framework.actions.Action;

public class CheckCoveragesInMatrixAction implements Action {
    @Override
    public boolean execute() {
        return new CheckCoveragesInMatrixUI().doShow();
    }

}
