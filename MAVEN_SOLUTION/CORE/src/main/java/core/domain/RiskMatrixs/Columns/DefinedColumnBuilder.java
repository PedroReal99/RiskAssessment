/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.Weight;
import eapli.framework.validations.Preconditions;
import java.util.Objects;

/**
 *
 * @author CarloS
 */
public class DefinedColumnBuilder extends ColumnBuilder {

    protected Weight weight;
    protected Contribution contribution;
    protected NeedOfAnalisys need;

    public DefinedColumnBuilder() {
        super();
    }

    protected DefinedColumnBuilder(RiskFactor risk, Weight weight, Contribution cont, NeedOfAnalisys need) {
        super(risk);
        this.weight = weight;
        this.contribution = cont;
        this.need = need;
    }

    /**
     * Instances a ColumnBuilder from another builder
     *
     * @param cb
     */
    public DefinedColumnBuilder(ColumnBuilder cb) {
        super(cb);
        if (cb instanceof DefinedColumnBuilder) {
            DefinedColumnBuilder other = (DefinedColumnBuilder) cb;
            this.weight = other.weight;
            this.contribution = other.contribution;
            this.need = other.need;
        }
    }

    /**
     * Instances a ColumnBuilder from a BaseColumn
     *
     * @param c
     */
    public DefinedColumnBuilder(BaseColumn c) {
        super(c);
        if (c instanceof DefinedColumn) {
            DefinedColumn other = (DefinedColumn) c;
            this.weight = other.weight;
            this.contribution = other.contribution;
            this.need = other.necessity;
        }
    }

    /**
     * Sets a new weight for this builder
     *
     * @param weight
     */
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    /**
     * Sets a new contribution for this builder
     *
     * @param contribution
     */
    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }

    /**
     * Sets a new need for this builder
     *
     * @param need
     */
    public void setNeed(NeedOfAnalisys need) {
        this.need = need;
    }

    /**
     * Validates column
     *
     * @return ture if and oly if the risk, weightm contribution and need are
     * not null
     */
    @Override
    public boolean validate() {
        return super.validate() && this.weight != null && this.contribution != null && this.need != null;
    }

    /**
     * Builds the column, if possible, else returns null
     *
     * @return
     */
    @Override
    public DefinedColumn build() {
        if (validate()) {
            return new DefinedColumn(risk, weight, contribution, need);
        }
        return null;
    }

    /**
     * Builds a valid, default column builder for this riskFactor: This means
     * that the column will have 0 weight, Positive contribution, Facultative
     * necessity
     *
     * @param risk
     * @return
     */
    @Override
    public DefinedColumnBuilder defaultBuilder(RiskFactor risk) {
        Preconditions.nonNull(risk, "Risk cannot be null!");
        DefinedColumnBuilder cb = new DefinedColumnBuilder();
        cb.setRiskFactor(risk);

        return new DefinedColumnBuilder(risk, new Weight(0), Contribution.POSITIVE, NeedOfAnalisys.FACULTATIVE);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + super.hashCode();
        hash = 97 * hash + Objects.hashCode(this.weight);
        hash = 97 * hash + Objects.hashCode(this.contribution);
        hash = 97 * hash + Objects.hashCode(this.need);
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
        final DefinedColumnBuilder other = (DefinedColumnBuilder) obj;
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        if (!Objects.equals(this.contribution, other.contribution)) {
            return false;
        }
        if (!Objects.equals(this.need, other.need)) {
            return false;
        }
        return super.equals(obj);
    }

}
