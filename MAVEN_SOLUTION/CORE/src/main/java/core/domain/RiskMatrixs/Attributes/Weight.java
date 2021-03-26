package core.domain.RiskMatrixs.Attributes;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class Weight implements ValueObject{

    private int weight;

    public Weight() {
        //Persistence
    }

    public Weight(int weight) {
        this.weight = weight;
    }

    public void changeweight(int weight) {
        this.weight = weight;
    }

    public int obtainWeight() {
        return this.weight;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.weight;
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
        final Weight other = (Weight) obj;
        if (this.weight != other.weight) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "core.domain.RiskMatrix.Weight[ weight=" + obtainWeight() + "]";
    }

}
