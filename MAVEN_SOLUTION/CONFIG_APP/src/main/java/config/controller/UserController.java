/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

import core.domain.User.Email;
import core.domain.User.Password;
import core.domain.User.Role;
import core.domain.User.RoleList;
import core.domain.User.User;
import core.persistence.PersistenceContext;
import core.persistence.UserRepository;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import java.util.ArrayList;

/**
 *
 * @author Vasco Rodrigues
 */
public class UserController {
    
    public User createUser(String email, String password, Role role) throws ConcurrencyException, IntegrityViolationException {
        try {
            User u = createUr(email, password, role);
            persistUser(u);
            return u;
        } catch(IllegalArgumentException ex) {
            return null;
        }
    }
    
    protected final User createUr(String email, String password, Role role) {
        try {
            RoleList r = new RoleList(new ArrayList<>());
            r.addRoleToUser(role);
            return new User(new Email(email), new Password(password), r);
        } catch(IllegalArgumentException ex) {
            return null;
        }
    }
    
    protected final void persistUser(User u) throws ConcurrencyException, IntegrityViolationException {
        UserRepository rp = obtainUserRepository();
        rp.save(u);
    }
    
    protected final UserRepository obtainUserRepository() {
        return PersistenceContext.repositories().UserRepository();
    }
    
}
