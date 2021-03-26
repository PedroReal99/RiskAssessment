/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.CaseAnalisysController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Carlos Coelhos
 */
public class ConfirmCaseAssessementUI extends AbstractUI {

    private final CaseAnalisysController c;

    public ConfirmCaseAssessementUI(CaseAnalisysController c) {
        this.c = c;
    }

    @Override
    public String headline() {
        return "Confirm case";
    }

    @Override
    protected boolean doShow() {
        String comment=Console.readLine("Do you wish to add any comment?\nPress \"ENTER\" to skip this step");
        c.addComment(comment);
        c.confirmAssessement();
        return false;
    }

}
