/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.RiskMatrixController;
import eapli.framework.actions.Action;

/**
 *
 * @author CarloS
 */
public class MatrixConfigShowAllAction implements Action {

    private MatrixConfigUI ui;

    public MatrixConfigShowAllAction(RiskMatrixController cont) {
        ui = new MatrixConfigUI(cont);
    }

    @Override
    public boolean execute() {
        return ui.showAll();
    }
}
