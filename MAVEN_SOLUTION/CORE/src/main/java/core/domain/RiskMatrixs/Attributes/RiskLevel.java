/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Attributes;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 *
 * @author morei
 */
@Embeddable
public class RiskLevel implements ValueObject {

    private int rLevel;

    protected RiskLevel() {

    }

    public RiskLevel(int rLevel) {
        this.rLevel = rLevel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.rLevel;
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
        final RiskLevel other = (RiskLevel) obj;
        if (this.rLevel != other.rLevel) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return ""+rLevel;
    }
    
    public int obtainRiskLevel() {
        return this.rLevel;
    }

}
