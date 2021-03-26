/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import eapli.framework.infrastructure.repositories.Repository;

/**
 *
 * @author Pedro
 */
public interface SurroundingRepository extends Repository <Surrounding,Long>{
    

    /**
     * Returns the object or null
     * @param type
     * @param name
     * @return 
     */
    public Surrounding findByBothParameters(STName type,SurroundingName name);
    
    public Iterable<Surrounding> findByDistrict(String district);
    
    public Iterable<Surrounding> findByCoordinates(String lat1, String lon1, String lat2, String lon2);
  
    
}
