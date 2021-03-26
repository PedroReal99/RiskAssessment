package core.domain.Insurance.ClassificationTables;

import core.domain.Insurance.Classification;
import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.RiskFactor;
import eapli.framework.domain.model.DomainEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.ElementCollection;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;

@Embeddable
public class ClassificationColumn implements DomainEntity<RiskFactor>, Serializable {

    @JoinColumn
    protected RiskFactor riskFactor;

    @ElementCollection
    protected List<String> relevantSurounds;

    @Embedded
    protected Classification classifiation;

    protected ClassificationColumn() {
        //For ORM only
    }

    public ClassificationColumn(RiskFactor riskFactor, Classification c) {
        this.riskFactor = riskFactor;
        this.classifiation = c;
        relevantSurounds = new ArrayList<>();
    }

    protected RiskFactor obtainRiskFacotr() {
        return this.riskFactor;
    }

    protected void updateClassification(String source) {
        this.classifiation = new Classification(Metric.valueOf(riskFactor.obtainMetrics().toUpperCase()).getClassification(source, relevantSurounds, riskFactor).toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.riskFactor);
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
        final ClassificationColumn other = (ClassificationColumn) obj;
        if (!Objects.equals(this.riskFactor, other.riskFactor)) {
            return false;
        }
        return true;
    }

    public Classification getClassification() {
        return this.classifiation;
    }

    public List<String> getRelevantSurroundings() {
        return new ArrayList<>(this.relevantSurounds);
    }

    public void setRelevantSurroundings(List<String> surr) {
        this.relevantSurounds = new ArrayList<>(surr);
    }

    public void addRelevantSurrounding(String toAdd) {
        this.relevantSurounds.add(toAdd);
    }

    public void removeRelevantSurrounding(int toRemove) {
        if (toRemove < this.relevantSurounds.size()) {
            this.relevantSurounds.remove(toRemove);
        }
    }

    @Override
    public String toString() {
        return "core.domain.RiskMatrix.Column[RiskFactor=" + this.riskFactor.toString() + "]";
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public RiskFactor identity() {
        return riskFactor;
    }

}
