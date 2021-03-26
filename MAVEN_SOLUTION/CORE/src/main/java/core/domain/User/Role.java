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
public class Role implements ValueObject {
    
    private String role;
    
    public static final Role GUESS = new Role("GUESS");
    public static final Role RISK_ANALIST = new Role("RISK_ANALIST");
    public static final Role ADMIN = new Role("ADMINISTRATOR");
    public static final Role UNDERWRITER = new Role("UNDERWRITER");
    public static final Role EXTERNAL_SYSTEM = new Role("EXTERNAL_SYSTEM");
    public static final Role GEOREFERENCE_SERVICE = new Role("GEOREFERENCE_SERVICE"); 
    public static final Role PROJECT_MANAGER = new Role("PROJECT_MANAGER"); 
    

    protected Role() {
        //ORM
    }

    private Role(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.role);
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
        final Role other = (Role) obj;
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "role= " + role;
    }
    
    
    
    
    
}
