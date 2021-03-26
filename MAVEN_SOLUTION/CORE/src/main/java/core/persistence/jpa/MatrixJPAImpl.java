package core.persistence.jpa;

import core.domain.RiskMatrixs.MatrixState;
import core.domain.RiskMatrixs.MatrixVersion;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.MatrixRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Query;

public class MatrixJPAImpl extends JpaAutoTxRepository<RiskMatrix, MatrixVersion, MatrixVersion> implements MatrixRepository {

    public MatrixJPAImpl(String persistenceUnitName) {
        super(persistenceUnitName, "version");
    }

    @Override
    public RiskMatrix findByName(String name) {
        Map<String, Object> param = new HashMap<>();

        Optional<RiskMatrix> ret = super.findById(MatrixVersion.create(name));
        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    @Override
    public RiskMatrix getInForceMatrix() {
        Map<String, Object> param = new HashMap<>();
        param.put("status", MatrixState.IN_FORCE);
        Optional<RiskMatrix> ret = super.matchOne("e.status = :status", param);
        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    @Override
    public List<RiskMatrix> findByState(MatrixState state) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", state);
        List<RiskMatrix> ret = super.match("e.status = :status", param);
        return ret;
    }
    
    
    

}



