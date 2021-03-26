/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.CaseAnalisysController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

/**
 *
 * @author Carlos Coelho
 */
public class ManuallySetResultsUI extends AbstractUI {

    private final CaseAnalisysController c;

    public ManuallySetResultsUI(CaseAnalisysController c) {
        this.c = c;
    }

    @Override
    public String headline() {
        return "Modify results";
    }

    @Override
    protected boolean doShow() {
        boolean updated = false;
        SelectWidget<String> widget = new SelectWidget<>("Select the insurance object to change", c.getResultsByName());
        do {
            widget.show();
            if (widget.selectedOption() != 0) {
                int riskIndex = Console.readInteger("new Risk Index");
                c.setRiskIndex(widget.selectedElement(), riskIndex);
                updated = true;
            }
        } while (widget.selectedOption() != 0);
        
        if(updated){
            String comment="";
            while((comment=Console.readLine("Please add a justification")).trim().isEmpty());
            c.addComment(comment);
        }
        c.confirmAssessement();
        return false;
    }

}
