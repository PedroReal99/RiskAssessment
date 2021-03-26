/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.CaseController;
import analysis.controller.LoginController;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author morei
 */
public class CasePendingUI extends AbstractUI {

    private final CaseController c = new CaseController();

    @Override
    public String headline() {
        return "List Pending Cases:";
    }

    @Override
    protected boolean doShow() {
        System.out.println(headline());
        String option, district, caseId;
        String state = "Em espera";
        CaseState s = CaseState.VALIDATING;
        List<CaseI> list = new ArrayList<>();
        do {
            option = Console.readLine(" 1-List all\n 2-List filter by district\n 3-AR ask for a require to review one of the cases \n 0-Exit\n\nChoose option:");
            switch (option) {
                case "1":
                    list = c.listCaseByStateWithoutEmployee(s);
                    printCode(list);
                    break;
                case "2":
                    district = Console.readLine("District:\n");
                    list = c.listCaseByStateWithDistrict("Em espera", district);
                    printCode(list);
                    break;
                case "3":
                    if (CaseController.verifyRole()) {
                        list = c.listCaseByStateWithoutEmployee(s);
                        printCode(list);
                        caseId = Console.readLine(" Id for the case to review for you");
                        CaseController.changeCaseAr(new CaseCode(caseId));
                        System.out.println("Alterado com sucesso\n");
                    } else {
                        System.out.println("O user que fez login nao tem permisão para esta operação\n");
                    }
                    break;
                case "0":
                    (new ExitUIAction()).execute();
            }
        } while ("1".equals(option) || "2".equals(option) || "3".equals(option));
        return true;
    }

    private void printCode(List<CaseI> list) {
        for (CaseI cc : list) {
            System.out.println("Code " + cc.obtainCode());
        }
    }

}
