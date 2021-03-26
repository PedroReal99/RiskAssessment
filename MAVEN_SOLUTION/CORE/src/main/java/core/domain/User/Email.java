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
public class Email implements ValueObject , Comparable<Email>{
    
    private String email;

    protected Email() {
        //ORM
    }

    public Email(String email) {
        if(email != null && !email.isEmpty() && email.matches("([A-Za-z0-9]+@[a-z]+.[a-z]+)")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email inválido " +email);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.email);
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
        final Email other = (Email) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "email= " + email;
    }

    @Override
    public int compareTo(Email t) {
        return this.email.compareTo(t.email);
    }
    
    public String obtainEmail() {
        return this.email;
    }
    
}
