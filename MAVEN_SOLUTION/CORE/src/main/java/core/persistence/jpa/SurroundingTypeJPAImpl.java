/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.domain.Surrounding.STName;
import core.domain.Surrounding.SurroundingType;
import core.persistence.SurroundingTypeRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Pedro
 */
public class SurroundingTypeJPAImpl  extends JpaAutoTxRepository<SurroundingType,STName,STName> implements SurroundingTypeRepository {

    
    
     public SurroundingTypeJPAImpl(TransactionalContext autoTx) {
        super(autoTx,"tipo");
    }

    public SurroundingTypeJPAImpl(String puname) {
        super(puname,"tipo");
    }

    @Override
    public SurroundingType findByName(STName name) {
        Map<String,Object> param=new HashMap<>();
        param.put("tipo", name);
        Optional<SurroundingType> ret= super.matchOne("e.tipo =:tipo",param);
        if(ret.isPresent()){
            return ret.get();
        }
        return null;
    }  

    @Override
    public Iterable<SurroundingType> findByDistrict(String district) { 
        Map<String,Object> param = new HashMap<>();
        param.put("district", district);
        List<SurroundingType> ret = super.match("e.district =:district", param);
        if(!ret.isEmpty()){
            return ret;
        }
        return null;
    }
}
