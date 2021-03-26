/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.LoginController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Vasco_Rodrigues
 */
public class LoginUI extends AbstractUI {
    
    private final LoginController lc = new LoginController();

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String emailTemp;
        String passwordTemp;      
        do {
            emailTemp = Console.readLine("Insert email:");
            passwordTemp = Console.readLine("Insert password:");
        } while(lc.loginRiskAnalysis(emailTemp, passwordTemp) == false);
        return true;
    }

    @Override
    public String headline() {
        return "Risk Analysis Login:";
    }
    
}
