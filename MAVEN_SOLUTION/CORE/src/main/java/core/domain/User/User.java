/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.User;

import eapli.framework.domain.model.AggregateRoot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Vasco_Rodrigues
 */
@Entity
public class User implements AggregateRoot<Email> , Serializable {
    
    @EmbeddedId
    private Email email;
    
    private Password password;
    
    private RoleList roles;

    protected User() {
        //ORM
    }

    public User(Email email, Password password, RoleList roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    
    public User(Email email, Password password, Role role) {
        this.email = email;
        this.password = password;
        RoleList r = new RoleList(new ArrayList<>());
        r.addRoleToUser(role);
        this.roles = r;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.email);
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
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", password=" + password + ", roles=" + roles + '}';
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Email identity() {
        return this.email;
    }
    
    public boolean verifyUserPassword(Password password) {
        return this.password.equals(password);
    }

    public boolean verifyRole(Role a) {
        return this.roles.verifyRole(a); 
    }

    public Email obtainEmail() { return email; }
}
