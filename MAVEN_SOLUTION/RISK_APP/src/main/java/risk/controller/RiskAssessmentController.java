/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.CaseRepository;
import core.persistence.InsuranceRepository;
import core.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.Insurance.InsuranceObject;
import java.util.List;

/**
 *
 * @author Vasco_Rodrigues
 */
public class RiskAssessmentController {
    
    /*
    True para ter detalhes e False para não ter detalhes
    */
    public boolean calculateRiskAssessment(RiskMatrix rm, CaseI c , boolean det) throws ConcurrencyException, IntegrityViolationException, IllegalArgumentException {
        verifyIfAbleToCalculateRiskIndex(rm, c);
        c.calculateRiskAssessment("risk app",rm, det);
        c.alterCaseStatus("risk app",CaseState.VALIDATING);
        persistCase(c);
        persistInsuranceObjects(c.getAssociatedInsuranceObjects());
        return true;
    }
    
    protected final void persistCase(CaseI c) throws ConcurrencyException, IntegrityViolationException {
        CaseRepository cr = obtainCaseRepository();
        cr.save(c);
    }
    
    protected final void persistInsuranceObjects(List<InsuranceObject> list) throws ConcurrencyException, IntegrityViolationException {
        InsuranceRepository ir = obtainInsuranceObjectRepository();
        list.forEach((is) -> {
            ir.save(is);
        }); 
    }
    
    protected final CaseRepository obtainCaseRepository() {
        return PersistenceContext.repositories().caseRepository();
    }
    
    protected final InsuranceRepository obtainInsuranceObjectRepository() {
        return PersistenceContext.repositories().insuranceRepository();
    }
    
    protected boolean verifyMatrix(RiskMatrix rm) {
        return rm.obtainMatrix().isEmpty();
    }
    
    protected boolean verifyCase(CaseI c) {
        return c.getAssociatedInsuranceObjects().isEmpty();
    }
    
    protected void verifyIfAbleToCalculateRiskIndex(RiskMatrix rm , CaseI c) {
        if(rm == null || verifyMatrix(rm)) {
            throw new IllegalArgumentException("There isn't any matrix, unable to perform a risk assessment");
        }
        if(c == null || verifyCase(c)) {
            throw new IllegalArgumentException("There isn't Coverages connected to the Case");
        }
    }
    
    public void calculateRiskAssessmentForComparison(RiskMatrix rm, CaseI c) {
        verifyIfAbleToCalculateRiskIndex(rm, c);
        c.calculateRiskAssessment("risk app",rm, false);
    }
}
