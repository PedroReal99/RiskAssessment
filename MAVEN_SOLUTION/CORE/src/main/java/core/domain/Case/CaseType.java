/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 *
 * @author morei
 */
@Embeddable
public class CaseType implements ValueObject{

    private String type;

    protected CaseType() {
        //For ORM
    }

    public CaseType(String type) {
        this.type = type;
    }

    public String obtainType() {
        return this.type;
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
        final CaseType other = (CaseType) obj;
        return Objects.equals(this.type, other.type);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public String toString() {
        return "Type: " + obtainType();
    }

}
