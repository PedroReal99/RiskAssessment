/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import eapli.framework.actions.Action;

/**
 *
 * @author Vasco_Rodrigues
 */
public class CreateCoverageAction implements Action {

    @Override
    public boolean execute() {
        return new CreateCoverageUI().doShow();
    }
    
}
