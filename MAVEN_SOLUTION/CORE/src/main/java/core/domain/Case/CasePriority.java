/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Ricardo Branco
 */
@Embeddable
public class CasePriority implements ValueObject{
    
    public static final CasePriority MAX = new CasePriority("maximum");
    public static final CasePriority MIN = new CasePriority("minimum");
    
    private String priority;
    
    protected CasePriority() {
        //for ORM
    }
    
    public CasePriority(String priority) {
        this.priority = priority;
    }
    
    @Override
    public String toString() {
        return "Priority: " + priority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.priority);
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
        final CasePriority other = (CasePriority) obj;
        if (!Objects.equals(this.priority, other.priority)) {
            return false;
        }
        return true;
    }
}
