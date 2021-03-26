/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.ExportMeaninfulRiskFactorsController;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.io.IOException;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ExportMeaninfulRiskFactorsUI extends AbstractUI {
    
    private final ExportMeaninfulRiskFactorsController c = new ExportMeaninfulRiskFactorsController();
    
    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        try {
            System.out.println(headline());
            String filename = Console.readLine("Filename:");
            return this.c.exportMeaninfulRiskFactorsController(filename);
        } catch (IOException ex) {
            System.out.println("Erro ao escrever no ficheiro");
            return false;
        }
    }

    @Override
    public String headline() {
        return "Export Meaninful Risk Factors:";
    }
    
}
