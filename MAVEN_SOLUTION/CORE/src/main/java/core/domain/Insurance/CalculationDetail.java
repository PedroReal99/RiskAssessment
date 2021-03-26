/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import eapli.framework.domain.model.ValueObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Vasco Rodrigues
 */
@Embeddable
public class CalculationDetail implements ValueObject, Serializable {
    
    private List<String> details;

    /**
     * protected constructor
     */
    protected CalculationDetail() {
        //persistence
    }

    /**
     * public constructor
     *
     * @param details
     */
    public CalculationDetail(List<String> details) {
        this.details = details;
    }

    /**
     * obtain details
     *
     * @return
     */
    public List<String> obtainDetails() {
        return details;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Calculation Details: " + details.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.details);
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
        final CalculationDetail other = (CalculationDetail) obj;
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        return true;
    }

    public void reset(){
        this.details=new ArrayList<>();
    }
    
    public void addDetailsOfCalculation(String t) {
        details.add(t);
    }

}
