/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.Surrounding.STName;
import core.domain.Surrounding.SurroundingType;
import eapli.framework.infrastructure.repositories.Repository;

/**
 *
 * @author Pedro
 */
public interface SurroundingTypeRepository extends Repository <SurroundingType,STName>{
    

    /**
     * Returns the object or null
     * @param name
     * @return 
     */
    public SurroundingType findByName(STName name);
    
    public Iterable<SurroundingType> findByDistrict(String district);
  
    
}
