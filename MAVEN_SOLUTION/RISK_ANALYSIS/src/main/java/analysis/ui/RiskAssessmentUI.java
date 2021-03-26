/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.LoginController;
import analysis.controller.RiskAssessmentController;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import core.domain.RiskMatrixs.RiskMatrix;
import core.persistence.PersistenceContext;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Vasco_Rodrigues
 */
public class RiskAssessmentUI extends AbstractUI {
    
    private final RiskAssessmentController rc = new RiskAssessmentController();

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String casecode = Console.readLine("Insert Case Code:");
        boolean verif = Console.readBoolean("Do you want an Risk Analist to verify the case?");
        RiskMatrix rm = PersistenceContext.repositories().riskMatrixRepository().getInForceMatrix();
        CaseI c = PersistenceContext.repositories().caseRepository().findByCaseCode(new CaseCode(casecode));
        rc.calculateRiskAssessment(LoginController.logged ,rm, c, true, verif);
        return true;
    }

    @Override
    public String headline() {
        return "Risk Analysis Login:";
    }
}