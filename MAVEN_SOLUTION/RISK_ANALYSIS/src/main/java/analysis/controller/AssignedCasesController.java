/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import analysis.utils.TimePassed;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.Insurance.InsuranceObject;
import core.domain.User.Role;
import core.persistence.PersistenceContext;
import dto.CaseTimeDTO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class AssignedCasesController {

    public List<CaseTimeDTO> listPendingCases(CaseState s) throws ParseException {
        List<CaseI> i = PersistenceContext.repositories().caseRepository().findAllByStateAssignedToMe(s, LoginController.logged.identity());
        List<CaseTimeDTO> listDTO = new ArrayList<>();
        for (CaseI c : i) {
            List<String> insuranceNames = new ArrayList<>();
            for(InsuranceObject io : c.getAssociatedInsuranceObjects()){
                insuranceNames.add(io.obtainName());
            }
            String str = TimePassed.TimePassed(c.obtainLastUpdatedDate());
            listDTO.add(new CaseTimeDTO(c.obtainCode(),str,insuranceNames));
        }
        return listDTO;
    }

    public static boolean verifyRole() {
        return (LoginController.logged.verifyRole(Role.RISK_ANALIST) || LoginController.logged.verifyRole(Role.ADMIN));
    }

}
