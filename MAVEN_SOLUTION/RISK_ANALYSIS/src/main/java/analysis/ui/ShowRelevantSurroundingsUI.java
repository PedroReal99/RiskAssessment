/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.CaseAnalisysController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

/**
 *
 * @author Carlos Coelho
 */
public class ShowRelevantSurroundingsUI extends AbstractUI {

    private final CaseAnalisysController c;

    public ShowRelevantSurroundingsUI(CaseAnalisysController c) {
        this.c = c;
    }

    @Override
    public String headline() {
        return "Modify results";
    }

    @Override
    protected boolean doShow() {
        SelectWidget<String> widget = new SelectWidget<>("Select a risk factors", c.getRiskFactors());
        do {
            widget.show();

            if (widget.selectedOption() != 0) {
                ListUI<String> list=new ListUI<>(c.getRelevantLocations(widget.selectedElement()),System.out::println, "surrounding","Relevant Surroundings");
                list.show();
                Console.readLine("");
            }
        } while (widget.selectedOption() != 0);
        return true;
    }

}
