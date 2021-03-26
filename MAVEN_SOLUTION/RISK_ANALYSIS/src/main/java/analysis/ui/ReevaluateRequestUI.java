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
 * @author Carlos Coelho
 */
public class ReevaluateRequestUI extends AbstractUI {

    private final CaseAnalisysController c;

    public ReevaluateRequestUI(CaseAnalisysController c) {
        this.c = c;
    }

    @Override
    public String headline() {
        return "Confirm case";
    }

    @Override
    protected boolean doShow() {
        if (!c.wasChanged()) {
            Console.readLine("There were no modification that require reavaluation, press \"ENTER\" to return");
            return true;
        }
        String comment;
        while ((comment = Console.readLine("Please add a justification for these modifications")).trim().isEmpty());
        c.addComment("Justifiation of reavaluation: "+comment);
        c.reevaluate();
        return false;
    }

}
