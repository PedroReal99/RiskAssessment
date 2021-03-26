/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.CoverageController;
import core.domain.Coverage.Coverage;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ListCoverageUI extends AbstractUI {
    
    private final CoverageController c = new CoverageController();
    
    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        Iterable<Coverage> list = c.getAll();
        if(!list.iterator().hasNext()) {
            System.out.println("There isn't any registered Coverages");
        } else {
            for(Coverage t : list) {
                System.out.println(t.toString());
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "List Coverages";
    }
    
    
}
