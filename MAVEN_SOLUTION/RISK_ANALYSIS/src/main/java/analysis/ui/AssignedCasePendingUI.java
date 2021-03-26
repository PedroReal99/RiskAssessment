/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.AssignedCasesController;
import analysis.utils.TimePassed;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import dto.CaseTimeDTO;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class AssignedCasePendingUI extends AbstractUI {

    private final AssignedCasesController c = new AssignedCasesController();

    @Override
    public String headline() {
        return "List My Pending Cases:";
    }

    @Override
    protected boolean doShow() {
        //System.out.println(headline());
        String option, district, caseId;
        String state = "Em espera";
        CaseState s = CaseState.VALIDATING;
        List<CaseTimeDTO> list = new ArrayList<>();
        do {
            System.out.println("");
            System.out.println(headline());
            option = Console.readLine(" 1-List all cases assigned to me\n 2-Start the analysis of a pending case.\n 0-Exit\n\nChoose option:");
            switch (option) {
                case "1":
            {
                try {
                    list = c.listPendingCases(s);
                } catch (ParseException ex) {
                    Logger.getLogger(AssignedCasePendingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            {
                try {
                    printCase(list);
                } catch (ParseException ex) {
                    Logger.getLogger(AssignedCasePendingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                case "2":
                    if (c.verifyRole()) {
                try {
                    list = c.listPendingCases(s);
                } catch (ParseException ex) {
                    Logger.getLogger(AssignedCasePendingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    printCase(list);
                } catch (ParseException ex) {
                    Logger.getLogger(AssignedCasePendingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                        String op=Console.readLine("Choose one of the cases above and write the number associated to start analysis.\n");
                        String code = list.get(Integer.parseInt(op)-1).CaseCode;
                        //Chamar o método do Carlos com o id lido
                    }else{
                        System.out.println("The user that is logged can´t access this operation");
                    }

                    break;
                case "0":
                    (new ExitUIAction()).execute();
            }
        } while ("1".equals(option) || "2".equals(option) || "3".equals(option));
        return true;
    }
    
     private void printCase(List<CaseTimeDTO> list) throws ParseException {
         int i=1;
        for (CaseTimeDTO cc : list) {
            String str="Insurance Objects Associated: ";
            for(String s: cc.listInsurances){
                str+=s+";";
            }
            System.out.println(i+" |"+str +"| Time passed since his attribution:" + cc.timePassed);
            i++;
        }
    }

}


