/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.MetricStrategy;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.SurroundingType;
import core.persistence.PersistenceContext;
import core.persistence.RiskFactorRepository;
import core.persistence.SurroundingTypeRepository;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

/**
 *
 * @author Vasco Rodrigues
 */
public class RiskFactorController implements Controller {

    public RiskFactor createRiskFactor(String metrics, String surroundingType) throws ConcurrencyException, IntegrityViolationException {
        RiskFactor f = createRF(metrics, surroundingType);
        persistRiskFactor(f);
        return f;
    }

    public Iterable<RiskFactor> listRiskFactors() {
        RiskFactorRepository rp = obtainRiskFactorRepository();
        return rp.findAll();
    }

    protected final RiskFactor createRF(String metrics, String surroundingType) {
        SurroundingType s = verifySurrondingTypeName(surroundingType);
        if (s == null) {
            throw new NullPointerException("Surrounding type"+surroundingType+" not found");
        } else {
            return new RiskFactor(Metric.valueOf(metrics.toUpperCase()), new STName(surroundingType));
        }
    }

    protected final SurroundingType verifySurrondingTypeName(String stname) {
        SurroundingTypeRepository sr = obtainSurrondingTypeRepository();
        return sr.findByName(new STName(stname));
    }

    protected final void persistRiskFactor(RiskFactor r) throws ConcurrencyException, IntegrityViolationException {
        RiskFactorRepository rp = obtainRiskFactorRepository();
        rp.save(r);
    }

    protected final RiskFactorRepository obtainRiskFactorRepository() {
        return PersistenceContext.repositories().riskRepository();
    }

    protected final SurroundingTypeRepository obtainSurrondingTypeRepository() {
        return PersistenceContext.repositories().surroundingTypeRepository();
    }

}
