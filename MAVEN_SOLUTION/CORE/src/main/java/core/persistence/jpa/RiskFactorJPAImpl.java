/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.applicationSetup.Application;
import core.domain.RiskFactors.MetricStrategy;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskFactors.RiskFactorKey;
import core.domain.Surrounding.STName;
import core.persistence.RiskFactorRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author Vasco_Rodrigues
 */
public class RiskFactorJPAImpl extends JpaAutoTxRepository<RiskFactor,RiskFactorKey,Integer> implements RiskFactorRepository {
    
    public RiskFactorJPAImpl(TransactionalContext autoTx) {
        super(autoTx,"fieldID");
    }
    
    public RiskFactorJPAImpl(String puname) {
        super(puname, "fieldID");
    }   
   
    /**
     * reads an entity RiskFactor given its metrics and surroundingtype name
     *
     * @param metrics
     * @param name
     * @return
     */
    @Override
    public RiskFactor findByBothParameters(MetricStrategy metrics, STName name) {
        Map<String,Object> param=new HashMap<>();
        
        Optional<RiskFactor> ret= super.findById(new RiskFactorKey(metrics,name));
        if(ret.isPresent()){
            return ret.get();
        }
        return null;
    }
}
