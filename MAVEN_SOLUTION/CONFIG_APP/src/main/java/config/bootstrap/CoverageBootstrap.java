/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import config.controller.CoverageController;
import core.domain.Coverage.Coverage;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import eapli.framework.actions.Action;

/**
 *
 * @author Vasco_Rodrigues
 */
public class CoverageBootstrap implements Action {

    private final ColumnBuilder[] builders;

    
    public CoverageBootstrap(ColumnBuilder[] builders){
        this.builders=builders;
    }
    
    @Override
    public boolean execute() {
        createCoverages();
        return true;
    }

    private void createCoverages() {
        CoverageController c = new CoverageController();
        Coverage cov=c.createCoverage("Incêndios");
        if(cov==null){
            cov=c.getCoverage("Incêndios");
        }
        cov.associateRisk(builders[0].getRisk());
        cov.associateRisk(builders[1].getRisk());
        cov=c.createCoverage("Inundações");
        if(cov==null){
            cov=c.getCoverage("Inundações");
        }
        cov.associateRisk(builders[1].getRisk());
        cov.associateRisk(builders[2].getRisk());
        cov=c.createCoverage("Tornados");
        if(cov==null){
            cov=c.getCoverage("Tornados");
        }
        cov.associateRisk(builders[2].getRisk());
    }
    
}
