package config.ui;

import eapli.framework.actions.Action;

public class SurroundingTypeByLocationAction implements Action {
    @Override
    public boolean execute() {
        return new SurroundingTypeByLocationUI().doShow();
    }

}
