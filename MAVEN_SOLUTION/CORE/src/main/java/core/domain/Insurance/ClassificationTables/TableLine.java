package core.domain.Insurance.ClassificationTables;

import core.domain.Coverage.Coverage;
import core.domain.RiskFactors.RiskFactor;
import eapli.framework.domain.model.DomainEntity;
import java.io.Serializable;
import java.util.HashMap;

import java.util.Map;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class TableLine implements DomainEntity<Coverage>, Serializable {

    @JoinColumn
    private Coverage coverage;

    @JoinColumn
    private Map<RiskFactor,ClassificationColumn> columns;
    
    protected TableLine() {
    }

    public TableLine(Map<RiskFactor,ClassificationColumn> column, Coverage coverage) {
        this.columns = new HashMap<>(column);
        this.coverage = coverage;
    }

    public void changeCoverage(Coverage coverage) {
        this.columns = new HashMap<>();
        this.coverage = coverage;
    }

    public Map<RiskFactor,ClassificationColumn> obtainColumns() {
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
        final TableLine other = (TableLine) obj;
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
