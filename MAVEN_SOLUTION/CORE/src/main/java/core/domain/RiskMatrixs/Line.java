package core.domain.RiskMatrixs;

import core.domain.RiskMatrixs.Columns.BaseColumn;
import core.domain.Coverage.Coverage;
import core.domain.RiskFactors.RiskFactor;
import eapli.framework.domain.model.DomainEntity;
import java.io.Serializable;


import java.util.Map;
import java.util.Objects;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

public class Line implements DomainEntity<Coverage>, Serializable {

    @Id
    @JoinColumn
    private Coverage coverage;

    @JoinColumn
    private Map<RiskFactor,BaseColumn> columns;

    protected Line() {
    }

    public Line(Map<RiskFactor,BaseColumn> columns, Coverage coverage) {
        this.columns = columns;
        this.coverage = coverage;
    }

    public void changeCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public Map<RiskFactor,BaseColumn> obtainColumns() {
        return this.columns;
    }

    public Coverage obtainCoverage() {
        return this.coverage;
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
        final Line other = (Line) obj;
        if (!Objects.equals(this.coverage, other.coverage)) {
            return false;
        }
        if (!Objects.equals(this.columns, other.columns)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Coverage identity() {
        return coverage;
    }
}
