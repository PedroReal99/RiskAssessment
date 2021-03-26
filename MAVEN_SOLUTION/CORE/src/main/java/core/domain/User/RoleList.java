/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.User;

import eapli.framework.domain.model.ValueObject;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Vasco_Rodrigues
 */
@Embeddable
public class RoleList implements ValueObject {
    
    private List<Role> roles;

    protected RoleList() {
        //ORM
    }

    public RoleList(List<Role> roles) {
        this.roles = roles;
    }
    
    public void addRoleToUser(Role r) {
        this.roles.add(r);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.roles);
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
        final RoleList other = (RoleList) obj;
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.roles.toString();
    }
    
    public boolean verifyRole(Role a) {
        return this.roles.contains(a);
    }
    
}
