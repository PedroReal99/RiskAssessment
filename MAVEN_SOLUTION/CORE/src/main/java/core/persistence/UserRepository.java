/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.domain.User.Email;
import core.domain.User.User;
import eapli.framework.infrastructure.repositories.Repository;

/**
 *
 * @author Vasco_Rodrigues
 */
public interface UserRepository extends Repository<User, Email>{
    
    /**
     * Returns User by its email
     *
     * @param
     * @return
     */
    public User obtainUserByEmail(Email email);
    
}
