package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
import eapli.framework.validations.Preconditions;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@DiscriminatorValue("Detailed")
public class DetailedColumn extends DefinedColumn {

    @Embedded
    protected Scale scale;

    protected DetailedColumn() {
        //
    }

    public DetailedColumn(RiskFactor riskFactor, Weight weight, Contribution contribution, NeedOfAnalisys necessity, Scale scale) {
        super(riskFactor, weight, contribution, necessity);
        this.scale = scale;

    }

    public void changeScale(Scale scale) {
        Preconditions.nonNull(scale);
        this.scale = scale;
    }

    public Scale obtainScale() {
        return this.scale;
    }

    @Override
    public String toString() {
        return "Defined Column: " + super.obtainRiskFacotr().toString()
                + "|W " + this.weight
                + "|C " + this.contribution
                + "|N " + this.necessity
                + "\n" + this.scale;
    }
}
