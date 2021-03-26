/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.CoverageController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import eapli.framework.application.Controller;

/**
 *
 * @author Isaías
 */
public class CreateCoverageUI extends AbstractUI {
    
    private final CoverageController c = new CoverageController();
    
    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String name = Console.readLine("Insert Coverage name:");
        return c.createCoverage(name)!=null;
    }

    @Override
    public String headline() {
        return "Create Coverage";
    }

}
