/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.main;


import config.bootstrap.CaseBootstrap;
import config.bootstrapper.InsuranceBootstrapper;
import config.ui.MainMenuAction;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;


/**
 * /**
 * /**
 *
 * @author caduc
 */
public class Main {

    public static void main(String[] args) throws ConcurrencyException, IntegrityViolationException {
       mainMenu();
    }
    
    protected static void mainMenu() {
        new InsuranceBootstrapper().execute();
        new CaseBootstrap().execute();
        while(new MainMenuAction().execute());     
    }

}
