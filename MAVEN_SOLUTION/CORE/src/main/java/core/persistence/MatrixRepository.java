package core.persistence;


import core.domain.RiskMatrixs.MatrixState;
import core.domain.RiskMatrixs.MatrixVersion;
import core.domain.RiskMatrixs.RiskMatrix;
import eapli.framework.infrastructure.repositories.Repository;
import java.util.List;


public interface MatrixRepository extends Repository <RiskMatrix,MatrixVersion>{

    /**
     * Finds a riskMatrix by its version
     * @param name
     * @return The found matrix, or null if none was found
     */
    public RiskMatrix findByName(String name);
    
     /**
     * Finds the In Force matrix
     * @return The found matrix, or null if none was found
     */
    public RiskMatrix getInForceMatrix();
    
    /**
     * Finds all the matrix with a certain state
     * @param state
     * @return list of matched matrixes
     */
    public List<RiskMatrix> findByState(MatrixState state);
}

