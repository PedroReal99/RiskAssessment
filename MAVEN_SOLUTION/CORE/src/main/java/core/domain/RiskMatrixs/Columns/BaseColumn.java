package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.RiskFactor;
import eapli.framework.domain.model.DomainEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Embeddable;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

@Embeddable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Estado")
@DiscriminatorValue("Base")
public class BaseColumn implements DomainEntity<RiskFactor>, Serializable {

    @JoinColumn
    protected RiskFactor riskFactor;

    protected BaseColumn() {
        //For ORM only
    }

    public BaseColumn(RiskFactor riskFactor) {
        this.riskFactor = riskFactor;
    }

    protected RiskFactor obtainRiskFacotr() {
        return this.riskFactor;
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
        final BaseColumn other = (BaseColumn) obj;
        if (!Objects.equals(this.riskFactor, other.riskFactor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Base column: "+this.riskFactor.toString();
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
