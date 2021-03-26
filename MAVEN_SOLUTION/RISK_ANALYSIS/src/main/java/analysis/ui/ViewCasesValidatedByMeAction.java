package analysis.ui;

import eapli.framework.actions.Action;

public class ViewCasesValidatedByMeAction implements Action {
    @Override
    public boolean execute() {
        return new ViewCasesValidatedByMeUI().doShow();
    }
}
