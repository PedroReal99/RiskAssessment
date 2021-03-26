/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import core.domain.User.Email;
import core.domain.User.Password;
import core.domain.User.Role;
import core.domain.User.User;
import core.persistence.PersistenceContext;
import core.persistence.UserRepository;

/**
 *
 * @author Vasco_Rodrigues
 */
public class LoginController {
    
    public static User logged;
    
    public boolean loginRiskAnalysis(String emailTemp, String passwordTemp) {
        try {
            Email email = new Email(emailTemp);
            Password password = new Password(passwordTemp);
            User u = obtainUserByEmail(email);
            if(u == null) {
                if(!verifyGuestLogin(emailTemp, passwordTemp)) {
                   System.out.println("User with email " +emailTemp+ " not found and mock failed!!!");
                   return false; 
                }
                return true;
            }     
            return verifyRegistedUserLogin(u, email, password);
        } catch(IllegalArgumentException ex) {
            System.out.println("Email or Password cannot be empty parameter or Email in wrong format!!!");
            return false;
        }
    }
    
    protected boolean verifyGuestLogin(String emailTemp, String passwordTemp) {
        if(emailTemp.equals(passwordTemp)) {
            logged = new User(new Email(emailTemp), new Password(passwordTemp), Role.GUESS);
            System.out.println("Login Sucessful!!!");
            return true;
        }
        System.out.println("Login Failed!!!");
        return false;
    }
    
    protected boolean verifyRegistedUserLogin(User u , Email email, Password password) {
        if(!u.verifyUserPassword(password)) {
            System.out.println("Wrong password for " +email.toString()+ " !!!");
            return false;
        }
        if(!(u.verifyRole(Role.ADMIN) || u.verifyRole(Role.RISK_ANALIST))) {
            System.out.println("User with " +email.toString()+ " doesn't have permission to login!!!");
            return false;
        }
        logged = u;
        System.out.println("Login Sucessful!!!");
        return true;
    }
    
    protected User obtainUserByEmail(Email email) {
        return obtainUserRepository().obtainUserByEmail(email);
    }
    
    protected UserRepository obtainUserRepository() {
        return PersistenceContext.repositories().UserRepository();
    }
}
