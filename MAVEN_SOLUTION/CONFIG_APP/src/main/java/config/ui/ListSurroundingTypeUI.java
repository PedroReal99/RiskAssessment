/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.SurroundingTypeController;
import core.domain.Surrounding.SurroundingType;
import eapli.framework.presentation.console.AbstractUI;

/**
 *
 * @author morei
 */
public class ListSurroundingTypeUI extends AbstractUI {
    
    SurroundingTypeController c = new SurroundingTypeController();

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        Iterable<SurroundingType> list = c.getAll();
        if(!list.iterator().hasNext()) {
            System.out.println("There isn't any registered Surrounding Type");
        } else {
            for(SurroundingType t : list) {
                System.out.println(t.toString());
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "List Surrounding Types";
    }
    
}
