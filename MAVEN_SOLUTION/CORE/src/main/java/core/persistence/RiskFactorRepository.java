/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.RiskFactors.MetricStrategy;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskFactors.RiskFactorKey;
import core.domain.Surrounding.STName;
import eapli.framework.infrastructure.repositories.Repository;

/**
 *
 * @author Vasco Rodrigues
 */
public interface RiskFactorRepository extends Repository <RiskFactor,RiskFactorKey>{

    /**
     * reads an entity given its metrics and surrounding type
     *
     * @param metrics
     * @param namesurroundingtype
     * @return
     */
    public RiskFactor findByBothParameters(MetricStrategy metrics, STName namesurroundingtype);
  
}
