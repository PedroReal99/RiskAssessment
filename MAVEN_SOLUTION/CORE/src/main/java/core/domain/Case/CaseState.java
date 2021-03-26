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
 * @author Pedro
 */
@Embeddable
public class CaseState implements ValueObject {

    public static final CaseState WAITING = new CaseState("Em espera");
    public static final CaseState PROCESSING = new CaseState("Em processamento");
    public static final CaseState VALIDATING = new CaseState("Em validacao");
    public static final CaseState PROCESSED = new CaseState("Processado");

    private String state;
    
    protected CaseState() {
        //Persistence
    }

    public CaseState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "State: " + state;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.state);
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
        final CaseState other = (CaseState) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

}
