/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import core.domain.Case.CaseI;
import core.domain.location.PostLocation;
import core.domain.location.Location;
import core.domain.Coverage.Coverage;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.ClassificationTable;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.RiskMatrix;
import eapli.framework.domain.model.AggregateRoot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 *
 * @author morei
 */
@Entity
public class InsuranceObject implements AggregateRoot<InsuranceName>, Serializable {

    @EmbeddedId
    private InsuranceName name;

    @JoinColumn
    private Location location;

    @Embedded
    private RiskIndex riskIndex;

    @Embedded
    private CalculationDetail details;

    @Embedded
    private ClassificationTable classification;
    


    protected InsuranceObject() {
        //Precistence
    }

    public InsuranceObject(InsuranceName name, Location location) {
        this.location = location;
        this.name = name;
        this.details = new CalculationDetail(new ArrayList<>());
        this.riskIndex = new RiskIndex(-1);
        this.classification = new ClassificationTable(new ArrayList<>());
    }

    public InsuranceObject(InsuranceName name, PostLocation location, CalculationDetail details, RiskIndex riskIndex, ClassificationTable classification) {
        this.location = location;
        this.name = name;
        this.details = details;
        this.riskIndex = riskIndex;
        this.classification = classification;
    }

    public void setCase(CaseI code){
        System.out.println("oof");
    }
    
    @Override
    public String toString() {
        return name.toString();
    }

    public String obtainDetails() {
        return this.details.toString();
    }

    public String obtainRiskIndex() {
        return this.riskIndex.toString();
    }

    public String obtainLocation() {
        return this.location.toString();
    }

    public String obtainDistrict() {
        return this.location.getDistrict();
    }

    public String obtainName() {
        return this.name.toString();
    }

    public ClassificationTable obtainClassificationTable() {
        return this.classification;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
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
        InsuranceObject other = (InsuranceObject) obj;
        return this.name.equals(other.name);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public InsuranceName identity() {
        return name;
    }

    public void addFinalValueOfCalculation(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("Value cannot negative");
        } else {
            this.riskIndex = new RiskIndex(v);
        }
    }

    /**
     * Adds a new coverage to this insurance object
     *
     * @param coverage
     * @param column
     */
    public void addCoverage(Coverage coverage, Map<RiskFactor, ClassificationColumn> column) {
        classification.addCoverage(coverage, column);
    }

    public void calculateFinalScore(RiskMatrix rm, boolean det) {
        addFinalValueOfCalculation(this.classification.calculateFinalScore(this.location.toString(),rm, details, det));
    }

    public int obtainIntegerRiskIndex() {
        return this.riskIndex.getRiskIndex();
    }
    
    public String formattedString() {
        String formatted = this.name.toString();

        for (String s : details.obtainDetails()) {
            formatted += "\n" + s;
        }

        return formatted;
    }

    public ClassificationTable getClassification() {
        return this.classification;
    }

}
