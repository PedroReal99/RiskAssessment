/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import core.domain.RiskMatrixs.Line;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

/**
 *
 * @author CarloS
 */
@Embeddable
public class Classification implements Serializable {
    //FIXME criar a classe

    private String designation;

    protected Classification() {
        //persistence
    }

    public Classification(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return this.designation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.designation);
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
        final Classification other = (Classification) obj;
        return Objects.equals(this.designation, other.designation);
    }

}
