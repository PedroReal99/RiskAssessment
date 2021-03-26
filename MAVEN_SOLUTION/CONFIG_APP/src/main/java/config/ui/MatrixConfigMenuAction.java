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
public class MatrixConfigMenuAction implements Action{
    private RiskMatrixController controller;
    
    public MatrixConfigMenuAction(){
        controller=new RiskMatrixController();
    }
    
    @Override
    public boolean execute() {
        while(new MatrixConfigMenuUI(controller).show());
        return true;
    }
}
