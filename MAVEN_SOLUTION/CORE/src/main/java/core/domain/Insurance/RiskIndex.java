/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import eapli.framework.domain.model.ValueObject;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Vasco Rodrigues
 */
@Embeddable
public class RiskIndex implements ValueObject, Serializable {

    private int riskIndex;

    protected RiskIndex() {
        // For ORM only
    }

    public RiskIndex(int risk) {
        this.riskIndex = risk;
    }

    protected int getRiskIndex() {
        return riskIndex;
    }

    protected void setRiskIndex(int riskIndex) {
        this.riskIndex = riskIndex;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.riskIndex;
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
        final RiskIndex other = (RiskIndex) obj;
        if (this.riskIndex != other.riskIndex) {
            return false;
        }
        return true;
    }

    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public String toString() {
        return "riskIndex= " + riskIndex;
    }

    public void addFinalValueOfCalculation(int finalv) {
        setRiskIndex(finalv);
    }

}
