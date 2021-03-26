/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import eapli.framework.actions.Action;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;

/**
 *
 * @author Ricardo Branco
 */
public class NeedOfAnalisysBootstrap implements Action {
    
    private final ColumnBuilder [] builders;
    
    public NeedOfAnalisysBootstrap(ColumnBuilder [] builders) {
        this.builders = builders;
    }

    @Override
    public boolean execute() {
        try {
            registerNeedOfAnalisys(TestDataConstants.NEED1,0);
            registerNeedOfAnalisys(TestDataConstants.NEED2,1);
            registerNeedOfAnalisys(TestDataConstants.NEED3,2);
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }
    
    private void registerNeedOfAnalisys(NeedOfAnalisys need,int index) {
        ((DefinedColumnBuilder)this.builders[index]).setNeed(need);
    }
}
