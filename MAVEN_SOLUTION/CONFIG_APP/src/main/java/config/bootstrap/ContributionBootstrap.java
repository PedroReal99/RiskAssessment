/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import eapli.framework.actions.Action;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;

/**
 *
 * @author Ricardo Branco
 */
public class ContributionBootstrap  implements Action {
    
    private ColumnBuilder [] builders;
    
    public ContributionBootstrap(ColumnBuilder [] builders) {
        this.builders = builders;
    }

    @Override
    public boolean execute() {
        try {
            registContribution(TestDataConstants.CONT1,0);
            registContribution(TestDataConstants.CONT2,1);
            registContribution(TestDataConstants.CONT3,2);
            return true;
        } catch (Exception ex) {
            System.out.println("Bootstrap error");
            return false;
        }
    }
    
    private void registContribution(Contribution contribution,int index) {
        ((DefinedColumnBuilder)builders[index]).setContribution(contribution);
    }
}
