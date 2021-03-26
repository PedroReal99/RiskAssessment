/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.bootstrap;

import config.controller.RiskFactorController;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Vasco_Rodrigues
 */
public class RiskFactorBootstrap implements Action {

    private final ColumnBuilder[] builders;

    public RiskFactorBootstrap(ColumnBuilder[] builders) {
        this.builders = builders;
    }

    @Override
    public boolean execute() {
        try {
            createMultipleRiskFactors("Distancia", "Bombeiros",0);
            createMultipleRiskFactors("Quantidade", "IE Saúde",1);
            createMultipleRiskFactors("Quantidade", "Zona Urbana",2);
        } catch (ConcurrencyException ex) {
            System.out.println("Concurrency problem detected");
            return false;
        } catch (IntegrityViolationException ex) {
            System.out.println("Integrity problem detected");
            return false;
        }
        return true;
    }

    protected void createMultipleRiskFactors(String metric, String type, int index) throws ConcurrencyException, IntegrityViolationException {
        RiskFactorController rc = new RiskFactorController();
        builders[index].setRiskFactor(rc.createRiskFactor(metric, type));
    }
}
