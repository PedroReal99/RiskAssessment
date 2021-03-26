/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskFactors;

import core.domain.Surrounding.STName;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CarloS
 */
public class RiskFactorKey implements Serializable{

    private MetricStrategy metrics;

    private STName namesurroundingtype;

    protected RiskFactorKey(){
        //Precistence
    }
    
    public RiskFactorKey(MetricStrategy m, STName name){
        this.metrics=m;
        this.namesurroundingtype=name;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.metrics);
        hash = 89 * hash + Objects.hashCode(this.namesurroundingtype);
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
        final RiskFactorKey other = (RiskFactorKey) obj;
        if (!Objects.equals(this.metrics, other.metrics)) {
            return false;
        }
        if (!Objects.equals(this.namesurroundingtype, other.namesurroundingtype)) {
            return false;
        }
        return true;
    }
    
    
}
