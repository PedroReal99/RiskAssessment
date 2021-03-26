package core.domain.RiskMatrixs.Attributes;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Designation implements ValueObject{

    String designation;

    public Designation() {
        //Persistence const
    }

    public Designation(String designation){
        this.designation=designation;
    }
    public void changeDesignation(String designation){
        this.designation=designation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.designation);
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
        final Designation other = (Designation) obj;
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        return true;
    }
    
    
    
    @Override
    public String toString(){
        return this.designation;
    }
}
