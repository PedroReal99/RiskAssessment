package core.domain.RiskMatrixs.Attributes;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class Contribution implements ValueObject{

    public static final Contribution POSITIVE = new Contribution("Positive");
    public static final Contribution NEGATIVE = new Contribution("Negative");
    
    private String contribution;

    protected Contribution() {
        //For ORM
    }

    private Contribution(String contribution) {
        this.contribution = contribution;
    }

    public String obtainContribution() {
        return this.contribution;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.contribution);
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
        final Contribution other = (Contribution) obj;
        return Objects.equals(this.contribution, other.contribution);
    }
    
    @Override
    public String toString() {
        return this.contribution;
    }
}
