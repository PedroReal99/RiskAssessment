/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import eapli.framework.actions.Action;

/**
 *
 * @author joaoflores
 */
public class RegisterSurroundingInACertainLocationAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterSurroundingInACertainLocationUI().doShow();
    }
}
