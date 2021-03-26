package core.domain.RiskMatrixs.Attributes;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class NeedOfAnalisys implements ValueObject {
    
    public static final NeedOfAnalisys OBLIGATORY = new NeedOfAnalisys("Obligatory");
    public static final NeedOfAnalisys FACULTATIVE = new NeedOfAnalisys("Facultative");

    private String need;
    
    protected NeedOfAnalisys() {
        //For ORM
    }

    private NeedOfAnalisys(String need) {
        this.need = need;
    }

    public String obtainNeedOfAnalisys(){
        return this.need;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final NeedOfAnalisys other = (NeedOfAnalisys) obj;
        return Objects.equals(this.need, other.need);
    }
    
    @Override
    public String toString() {
        return this.need;
    }
}
