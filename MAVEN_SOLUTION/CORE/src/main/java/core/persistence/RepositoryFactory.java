/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import eapli.framework.domain.repositories.TransactionalContext;

/**
 *
 * @author morei
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the
     * repositories
     *
     * @return
     */
    //TransactionalContext buildTransactionalContext();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    RiskFactorRepository riskRepository(TransactionalContext autoTx); 
    
    /**
     *
     * @return
     */
    RiskFactorRepository riskRepository();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    CoverageRepository coverageRepository(TransactionalContext autoTx);
    CoverageRepository coverageRepository();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    SurroundingTypeRepository surroundingTypeRepository(TransactionalContext autoTx);
    
    SurroundingTypeRepository surroundingTypeRepository();
    
    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    SurroundingRepository surroundingRepository(TransactionalContext autoTx);
    
    SurroundingRepository surroundingRepository();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    MatrixRepository riskMatrixRepository(TransactionalContext autoTx);
    MatrixRepository riskMatrixRepository();
    
    
    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    CaseRepository caseRepository(TransactionalContext autoTx);
    CaseRepository caseRepository();
    
    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    InsuranceRepository insuranceRepository(TransactionalContext autoTx);
    InsuranceRepository insuranceRepository();
    
    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    UserRepository UserRepository(TransactionalContext autoTx);
    UserRepository UserRepository();
    
    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    LocationRepository LocationRepository(TransactionalContext autoTx);
    LocationRepository LocationRepository();
}
