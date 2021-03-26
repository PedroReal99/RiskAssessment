/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.RiskFactorController;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Vasco_Rodrigues
 */
public class CreateRiskFactorUI extends AbstractUI {
    
    private final RiskFactorController c = new RiskFactorController();
    
    protected Controller controller() {
        return this.c;
    }

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String metrics = Console.readLine("Insert metrics:");
        String stname = Console.readLine("Insert the Surronding Type Name");
        try {
            this.c.createRiskFactor(metrics, stname);
        } catch(IllegalArgumentException ex1) {
            System.out.println("Invalid Metrics");
        } catch(NullPointerException ex2) {
            System.out.println("Surronding Type Name unavailable");
        } catch (ConcurrencyException ex) {
            System.out.println("Concurrency problem detected");
        } catch (IntegrityViolationException ex) {
            System.out.println("Integrity problem detected");
        }
        return true;
    }

    @Override
    public String headline() {
        return "Risk Factor Creation:";
    }
    
}
