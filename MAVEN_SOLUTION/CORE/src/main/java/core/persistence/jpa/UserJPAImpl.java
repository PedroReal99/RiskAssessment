/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence.jpa;

import core.domain.User.Email;
import core.domain.User.User;
import core.persistence.UserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Vasco_Rodrigues
 */
public class UserJPAImpl extends JpaAutoTxRepository<User, Email,Email> implements UserRepository{

    public UserJPAImpl(String persistenceUnitName) {
        super(persistenceUnitName,"name");
    }

    @Override
    public User obtainUserByEmail(Email email) {
        Map<String,Object> param=new HashMap<>();
        param.put("email", email);
        Optional<User> ret= super.matchOne("e.email = :email",param);
        if(ret.isPresent()){
            return ret.get();
        }
        return null;
    }
    
}
