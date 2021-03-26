/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskFactors;

import core.domain.Surrounding.STName;
import eapli.framework.domain.model.AggregateRoot;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 *
 * @author Vasco Rodrigues
 */
@Entity
@IdClass (RiskFactorKey.class)
public class RiskFactor implements AggregateRoot<RiskFactorKey>, Serializable {
    
    
    @Id
    private MetricStrategy metrics;
    

    @Id
    private STName namesurroundingtype;

    private int fieldID;
    
    protected RiskFactor() {
        // For ORM only
    }
    
    public RiskFactor(MetricStrategy m, STName namesurroundingtype) {
        this.metrics = m;
        this.namesurroundingtype = namesurroundingtype; 
        fieldID=hashCode();
    }
    
    @Override
    public String toString() {
        return metrics.toString() + " + " +namesurroundingtype.toString();
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.metrics);
        hash = 97 * hash + Objects.hashCode(this.namesurroundingtype);
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
        final RiskFactor other = (RiskFactor) obj;
        if (!Objects.equals(this.metrics, other.metrics)) {
            return false;
        }
        if (!Objects.equals(this.namesurroundingtype, other.namesurroundingtype)) {
            return false;
        }
        return true;
    }

    public String obtainMetrics() {
        return this.metrics.toString();
    }
    
    public String obtainSTName() {
        return this.namesurroundingtype.toString();
    }
  
    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public RiskFactorKey identity() {
        return new RiskFactorKey(metrics, namesurroundingtype);
    }
}
