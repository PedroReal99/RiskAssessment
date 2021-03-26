/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Attributes;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.util.Pair;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 *
 * @author morei
 */
@Embeddable
public class Scale implements ValueObject {

    public static final Designation LOW = new Designation("LOW");
    public static final Designation MEDIUM = new Designation("MEDIUM");
    public static final Designation HIGH = new Designation("HIGH");

    @Embedded
    private List<Pair<Designation, RiskLevel>> scale;

    protected Scale() {
        //
    }

    public Scale(RiskLevel riskLevelLow, RiskLevel riskLevelMedium, RiskLevel riskLevelHigh) {
        Preconditions.nonNull(riskLevelHigh,riskLevelLow,riskLevelMedium);
        this.scale = new ArrayList<>();
        this.scale.add(new Pair<>(LOW, riskLevelLow));
        this.scale.add(new Pair<>(MEDIUM, riskLevelMedium));
        this.scale.add(new Pair<>(HIGH, riskLevelHigh));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.scale);
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
        final Scale other = (Scale) obj;
        if (!Objects.equals(this.scale, other.scale)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Scale "
                + "\nLow  : " + scale.get(0).getValue()
                + "\nMed  : " + scale.get(1).getValue()
                + "\nHigh : " + scale.get(2).getValue()
                + "\n";
    }
    
    public int obtainRiskLevelValue(Designation d) {
        Iterator<Pair<Designation, RiskLevel>> degit = scale.iterator();
        while(degit.hasNext()) {
            Pair<Designation, RiskLevel> aux = degit.next();
            if(aux.getKey().equals(d)) {
                return aux.getValue().obtainRiskLevel();
            }
        }
        return -1;
    }
    
    public List<Pair<Designation, RiskLevel>> obtainScale() {
        return this.scale;
    }

}
