/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.RiskFactorController;
import core.domain.RiskFactors.RiskFactor;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ListRiskFactorUI extends AbstractUI {
    
    private final RiskFactorController c = new RiskFactorController();
    
    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        Iterable<RiskFactor> list = c.listRiskFactors();
        if(!list.iterator().hasNext()) {
            System.out.println("There isn't any registered Risk Factors");
        } else {
            for(RiskFactor t : list) {
                System.out.println(t.toString());
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "List Risk Factors:";
    }
}
