/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.User;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Vasco_Rodrigues
 */
@Embeddable
public class Password implements ValueObject {
    
    private String password;

    protected Password() {
        //ORM
    }

    public Password(String password) {
        if(password != null && !password.isEmpty()) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Email inválido" +password);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.password);
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
        final Password other = (Password) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
