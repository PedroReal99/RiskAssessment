/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author CarloS
 */
@Embeddable
public class MatrixState implements ValueObject {

    private String status;

    public static final MatrixState BASE = new MatrixState("BASE");
    public static final MatrixState DEFINED = new MatrixState("DEFINED");
    public static final MatrixState DETAILED = new MatrixState("DETAILED");
    public static final MatrixState PUBLISHED = new MatrixState("PUBLISHED");
    public static final MatrixState IN_FORCE = new MatrixState("IN_FORCE");
    public static final MatrixState OBSOLETE = new MatrixState("OBSOLETE");

    protected MatrixState() {
        //Persistence
    }

    private MatrixState(String state) {
        this.status = state;
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.status);
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
        final MatrixState other = (MatrixState) obj;
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }

}
