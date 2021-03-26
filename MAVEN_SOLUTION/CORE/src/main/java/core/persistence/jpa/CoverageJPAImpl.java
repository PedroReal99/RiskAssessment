/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.domain.Coverage.Coverage;
import core.persistence.CoverageRepository;
import core.domain.Coverage.CoverageName;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author morei
 */
public class CoverageJPAImpl extends JpaAutoTxRepository<Coverage, CoverageName,CoverageName> implements CoverageRepository {

    public CoverageJPAImpl(String persistenceUnitName) {
        super(persistenceUnitName,"name");
    }

    @Override
    public Coverage findByName(CoverageName name) {
        Map<String,Object> param=new HashMap<>();
        param.put("name", name);
        Optional<Coverage> ret= super.matchOne("e.name = :name",param);
        if(ret.isPresent()){
            return ret.get();
        }
        return null;
    }

}
