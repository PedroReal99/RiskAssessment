/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import eapli.framework.actions.Action;

/**
 *
 * @author CarloS
 */
public class ScaleBootstrap implements Action{
    ColumnBuilder [] builders;
    
    public ScaleBootstrap (ColumnBuilder [] builders){
        this.builders=builders;
    }
    
    @Override
    public boolean execute() {
        try {
            registerScale(TestDataConstants.SCL1, 0);
            registerScale(TestDataConstants.SCL1, 1);
            registerScale(TestDataConstants.SCL1, 2);
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }
    
    public void registerScale(Scale scale, int index) {
        ((DetailedColumnBuilder)builders[index]).setScale(scale);
    }
}
