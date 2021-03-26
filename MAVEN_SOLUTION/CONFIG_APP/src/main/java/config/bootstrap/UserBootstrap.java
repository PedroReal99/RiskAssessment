/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import config.controller.UserController;
import core.domain.User.Role;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

/**
 *
 * @author Vasco Rodrigues
 */
public class UserBootstrap implements Action {
    
    private static final UserController UC = new UserController();

    @Override
    public boolean execute() {
        try {
            UC.createUser("guess@gmail.com", "guess" , Role.GUESS);
            UC.createUser("ar1@gmail.com","ar1" , Role.RISK_ANALIST);
            UC.createUser("admin1@gmail.com" , "admin1", Role.ADMIN);
        } catch (ConcurrencyException ex) {
            System.out.println("Concurrency problem detected");
            return false;
        } catch (IntegrityViolationException ex) {
            System.out.println("Integrity problem detected");
            return false;
        }
        return true;
    }
    
}
