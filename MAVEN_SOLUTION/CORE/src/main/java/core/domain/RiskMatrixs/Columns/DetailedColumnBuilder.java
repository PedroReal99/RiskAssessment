/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
import eapli.framework.validations.Preconditions;
import java.util.Objects;

/**
 *
 * @author CarloS
 */
public class DetailedColumnBuilder extends DefinedColumnBuilder {

    protected Scale scale;

    protected DetailedColumnBuilder() {
        super();
    }

    private DetailedColumnBuilder(RiskFactor risk, Weight weight, Contribution cont, NeedOfAnalisys need, Scale scale) {
        super(risk, weight, cont, need);
        this.scale = scale;
    }

    /**
     * Instances a ColumnBuilder from another builder
     *
     * @param cb
     */
    public DetailedColumnBuilder(ColumnBuilder cb) {
        super(cb);
        if (cb instanceof DetailedColumnBuilder) {
            this.scale = ((DetailedColumnBuilder) cb).scale;
        }
    }

    /**
     * Instances a ColumnBuilder from a BaseColumn
     *
     * @param c
     */
    public DetailedColumnBuilder(BaseColumn c) {
        super(c);
        if (c instanceof DetailedColumn) {
            this.scale = ((DetailedColumn) c).scale;
        }
    }

    /**
     * Sets a new scale for this builder
     *
     * @param scale
     */
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    /**
     * Validates the column
     *
     * @return true if and only if the scale is not null and super.validate() is
     * also true
     */
    @Override
    public boolean validate() {
        return this.scale != null && super.validate();
    }

    /**
     * Builds the column, if possible, else returns null
     *
     * @return
     */
    @Override
    public DetailedColumn build() {
        if (validate()) {
            return new DetailedColumn(risk, weight, contribution, need, scale);
        }
        return null;
    }

    /**
     * Builds a valid, default column builder for this riskFactor: This means
     * that the column will have 0 weight, Positive contribution, Facultative
     * necessity and 0 scale
     *
     * @param risk
     * @return
     */
    @Override
    public DetailedColumnBuilder defaultBuilder(RiskFactor risk) {
        Preconditions.nonNull(risk, "Risk cannot be null!");

        RiskLevel rl = new RiskLevel(0);

        return new DetailedColumnBuilder(risk, new Weight(0), Contribution.POSITIVE,
                NeedOfAnalisys.FACULTATIVE, new Scale(rl, rl, rl));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + super.hashCode();
        hash = 23 * hash + Objects.hashCode(this.scale);
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
        final DetailedColumnBuilder other = (DetailedColumnBuilder) obj;
        if (!Objects.equals(this.scale, other.scale)) {
            return false;
        }
        return super.equals(obj);
    }

}
