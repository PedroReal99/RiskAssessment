/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Coverage;

import core.domain.RiskFactors.RiskFactor;
import eapli.framework.domain.model.AggregateRoot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;

/**
 *
 * @author morei
 */
@Entity
public class Coverage implements AggregateRoot<CoverageName>, Serializable {

    @EmbeddedId
    private CoverageName name;

    //Risk factors that are relevant for the coverage
    @ManyToMany
    @JoinColumns({
        @JoinColumn(name = "metrics", referencedColumnName = "metrics")
        ,
        @JoinColumn(name = "namesurroundingtype", referencedColumnName = "namesurroundingtype")
    })
    private Set<RiskFactor> risks;

    protected Coverage() {
        //Precistence
    }

    public Coverage(CoverageName name) {
        this.name = name;
        risks = new HashSet<>();
    }

    public Coverage(CoverageName name, Set<RiskFactor> risks) {
        this.name = name;
        this.risks = new HashSet<>(risks);
    }

    /**
     * Associates a new relevant risk to this coverage
     *
     * @param risk
     */
    public void associateRisk(RiskFactor risk) {
        risks.add(risk);
    }

    /**
     * Returns a lsit of all the associated riskfactors
     *
     * @return
     */
    public List<RiskFactor> getAssociatedRiskFactors() {
        return new ArrayList<>(risks);
    }

    @Override
    public CoverageName identity() {

        return name;
    }

    @Override
    public String toString() {
        return name.toString();
    }

    public String obtainName() {
        return this.name.toString();
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
        Coverage other = (Coverage) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

}
