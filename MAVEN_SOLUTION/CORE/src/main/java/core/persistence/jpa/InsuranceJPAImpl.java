/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.applicationSetup.Application;
import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import core.persistence.InsuranceRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Pedro
 */
public class InsuranceJPAImpl extends JpaAutoTxRepository<InsuranceObject, InsuranceName, InsuranceName> implements InsuranceRepository{
    

    public InsuranceJPAImpl(String puname) {
        super(puname,"name");
    }

    @Override
    public InsuranceObject findByParameters(InsuranceName in) {
        
         Map<String,Object> param=new HashMap<>();
        param.put("name", in);
        Optional<InsuranceObject> ret= super.matchOne("e.name = :name",param);
        if(ret.isPresent()){
            return ret.get();
        }
        return null;
    }
    
}
