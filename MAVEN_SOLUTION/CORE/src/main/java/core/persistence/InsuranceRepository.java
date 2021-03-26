/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import eapli.framework.infrastructure.repositories.Repository;

/**
 *
 * @author Pedro
 */
public interface InsuranceRepository extends Repository<InsuranceObject,InsuranceName>{
    
    public InsuranceObject findByParameters(InsuranceName in);
    
}
