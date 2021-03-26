/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.applicationSetup.Application;
import core.persistence.CaseRepository;
import core.persistence.CoverageRepository;
import core.persistence.InsuranceRepository;
import core.persistence.MatrixRepository;
import core.persistence.RepositoryFactory;
import core.persistence.RiskFactorRepository;
import core.persistence.SurroundingRepository;
import core.persistence.SurroundingTypeRepository;
import eapli.framework.domain.repositories.TransactionalContext;

/**
 *
 * @author CarloS
 */
public class JPARepositoryFactory implements RepositoryFactory {

    /*
    NOT WORKING
    @Override
    public TransactionalContext buildTransactionalContext() {
        return new JpaTransactionalContext(Application.settings().getPersistenceUnitName(),Application.settings().getExtendedPersistenceProperties());
    }*/
    @Override
    public RiskFactorRepository riskRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CoverageRepository coverageRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SurroundingTypeRepository surroundingTypeRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MatrixRepository riskMatrixRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public CaseRepository caseRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public InsuranceRepository insuranceRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SurroundingRepository surroundingRepository() {
        return new SurroundingJPAImpl(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RiskFactorRepository riskRepository() {
        return new RiskFactorJPAImpl(Application.settings().getPersistenceUnitName() );
    }

    @Override
    public CoverageRepository coverageRepository() {
        return new CoverageJPAImpl(Application.settings().getPersistenceUnitName());
    }

    @Override
    public MatrixRepository riskMatrixRepository() {
        return new MatrixJPAImpl(Application.settings().getPersistenceUnitName());
    }

    @Override
    public CaseRepository caseRepository() {
        return new CaseJPAImpl(Application.settings().getPersistenceUnitName());
    }
    
    @Override
    public InsuranceRepository insuranceRepository() {
        return new InsuranceJPAImpl(Application.settings().getPersistenceUnitName());
    }

    @Override
    public core.persistence.UserRepository UserRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Do not Implement until buildTransactionalContext is functional"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public core.persistence.UserRepository UserRepository() {
        return new UserJPAImpl(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SurroundingTypeRepository surroundingTypeRepository() {
        return new SurroundingTypeJPAImpl(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SurroundingRepository surroundingRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public core.persistence.LocationRepository LocationRepository(TransactionalContext autoTx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public core.persistence.LocationRepository LocationRepository() {
        return new LocationJPAImpl(Application.settings().getPersistenceUnitName());
    }

}
