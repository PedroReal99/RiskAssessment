/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.RiskFactor;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.validations.Preconditions;
import java.util.Objects;

/**
 *
 * @author CarloS
 */
public class ColumnBuilder implements DomainFactory<BaseColumn> {

    /**
     * State of the column
     */
    public static enum State {
        BASE, DETAILED, DEFINED
    }

    /**
     * RiskFactor of the future column
     */
    protected RiskFactor risk;

    /**
     * 
     * @param state
     * @return 
     */
    public static ColumnBuilder newColumnBuilder(State state) {
        if (state == State.BASE) {
            return new ColumnBuilder();
        }
        if (state == State.DETAILED) {
            return new DetailedColumnBuilder();
        }
        return new DefinedColumnBuilder();

    }


    /**
     * 
     */
    protected ColumnBuilder() {
        //
    }
    

    /**
     * Instances a Column Builder for a risk
     * @param risk 
     */
    protected ColumnBuilder(RiskFactor risk) {
        this.risk=risk;
    }
    
    /**
     * Instances a ColumnBuilder from another builder
     * @param cb 
     */
    public ColumnBuilder(ColumnBuilder cb){
        this.risk=cb.risk;
    }

    /**
     * Instances a ColumnBuilder from a BaseColumn 
     * @param c 
     */
    public ColumnBuilder(BaseColumn c){
        this.risk=c.riskFactor;
    }
    
    /**
     * Returns the risk of this column
     * @return 
     */
    public RiskFactor getRisk(){
        return risk;
    }
    
    /**
     * Sets the risdk of this column
     * @param risk 
     */
    public void setRiskFactor(RiskFactor risk) {
        this.risk = risk;
    }

    /**
     * Validates column
     * @return true if the risk is not null
     */
    public boolean validate() {
        return this.risk != null;
    }

    /**
     * Builds the column, if possible, else returns null
     * @return 
     */
    @Override
    public BaseColumn build() {
        if (validate()) {
            return new BaseColumn(risk);
        }
        return null;
    }

    /**
     * Builds a valid, default column builder for this riskFactor:
     * On this class, it is the same as creating a new builder with this risk factor
     * @param risk
     * @return
     */
    public ColumnBuilder defaultBuilder(RiskFactor risk) {
        Preconditions.nonNull(risk,"Risk cannot be null!");  
        return new ColumnBuilder(risk);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.risk);
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
        final ColumnBuilder other = (ColumnBuilder) obj;
        if (!Objects.equals(this.risk, other.risk)) {
            return false;
        }
        return true;
    }
    
    

}
