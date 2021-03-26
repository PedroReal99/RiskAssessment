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
public class ExportCaseUI extends AbstractUI {

    private final CaseAnalisysController c;

    public ExportCaseUI(CaseAnalisysController c) {
        this.c = c;
    }

    @Override
    public String headline() {
        return "Export case";
    }

    @Override
    protected boolean doShow() {
        String path = Console.readLine("Choose save director, current\n" + System.getProperty("user.dir"));
        String basePath = System.getProperty("user.dir") + "/";
        if (!path.trim().isEmpty()) {
            basePath += path;
            if (basePath.charAt(basePath.length() - 1) != '/') {
                basePath += "/";
            }
        }
        c.exportToXml(basePath);
        return true;
    }

}
