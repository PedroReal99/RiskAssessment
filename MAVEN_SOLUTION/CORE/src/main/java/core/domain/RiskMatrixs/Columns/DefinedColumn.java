package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.Weight;
import eapli.framework.validations.Preconditions;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Embeddable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="State")
@DiscriminatorValue("Defined")
public class DefinedColumn extends BaseColumn {
   
   @Embedded
   protected  Weight weight;
   
   @Embedded
   protected NeedOfAnalisys necessity;
   
   @Embedded
   protected Contribution contribution;



    protected DefinedColumn(){
        //
    }

    public DefinedColumn(RiskFactor riskFactor, Weight weight, Contribution contribution, NeedOfAnalisys necessity){
        super(riskFactor);
        this.weight=weight;
        this.necessity=necessity;
        this.contribution=contribution;
    }
    public DefinedColumn(RiskFactor riskFactor, Weight weight){
        super(riskFactor);
        this.weight=weight;
        this.necessity=null;
        this.contribution=null;
    }

    public void changeNecessity(NeedOfAnalisys necessity){
        Preconditions.nonNull(necessity);
        this.necessity=necessity;
    }

    public void changeWeight(Weight weight) {
        Preconditions.nonNull(weight);
        this.weight = weight;
    }

    public void changeContribution(Contribution contribution) {
        Preconditions.nonNull(contribution);
        this.contribution = contribution;
    }


    public Weight obtainWeight() {
        return this.weight;
    }

    public NeedOfAnalisys obtainNecessity() {
        return this.necessity;
    }

    public Contribution obtainContribution() {
        return this.contribution;
    }
    public RiskFactor obtainRiskFactor(){
        return super.obtainRiskFacotr();
    }

    @Override
    public String toString() {
        return "Defined Column: " +super.obtainRiskFacotr().toString()
                + "|W " + this.weight
                + "|C " +this.contribution
                +"|N "+ this.necessity;
    }

}
