package core.domain.RiskMatrixs;

import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.Designation;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import javax.persistence.Entity;
import java.util.Objects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;

@Entity
public class RiskMatrix implements AggregateRoot<MatrixVersion>, Serializable {

    @EmbeddedId
    protected MatrixVersion version;

    @Embedded
    protected MatrixState status;

    @JoinColumn
    private List<Line> listofLines = new ArrayList<>();

    protected RiskMatrix() {
        // For ORM only
    }

    public RiskMatrix(MatrixVersion v, MatrixState s, List<Line> list) {
        this.version = v;
        this.status = s;
        this.listofLines = list;

    }

    public MatrixVersion getVersion() {
        return version;
    }

    public MatrixState getState() {
        return status;
    }

    
    
    public List<Line> obtainMatrix() {
        return this.listofLines;
    }

    /**
     * Creates a new MatrixBuilder, if possible from a matrix. Matrixes strate
     * cannot be base
     *
     * @param m
     * @return
     */
    public static MatrixBuilder startFromMatrix(RiskMatrix m) {
        Preconditions.nonNull(m, "RiskMatrix cannot be null");
        if (m.status.equals(MatrixState.BASE)) {
            throw new IllegalArgumentException("This matrix is not Caracterized nor Detailed!");
        }
        return new MatrixBuilder(m);
    }

    /**
     * Changes the State of the matrix to Published, if possible
     * @return true if successful
     */
    public boolean publish(String date){
       if(!status.equals(MatrixState.DETAILED)){
            return false;
        }
        if(!version.publishVersion(date)){
            return false;
        }
        status=MatrixState.PUBLISHED;
        return true;
    }
    
    /**
     * Checks if the matrix can be inForced
     * @return true if successful
     */
    public boolean canBeInForce(){
        return version.canBeInForce();
    }
    
    /**
     * Changes the State of the matrix to Obsolete, if possible
     * @return true if successful
     */
    public boolean turnObsolete(){
        if(!status.equals(MatrixState.IN_FORCE)){
            return false;
        }
        status=MatrixState.OBSOLETE;
        return true;
    }
    
    /**
     * Changes the State of the matrix to In Force, if possible
     * @return true if successful
     */
    public boolean turnInForce(){
        if(!version.canBeInForce()){
            return false;
        }
        status=MatrixState.IN_FORCE;
        return true;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.version);
        hash = 13 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RiskMatrix other = (RiskMatrix) obj;
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public MatrixVersion identity() {
        return this.version;
    }

}
