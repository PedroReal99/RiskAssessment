/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CasePriority;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.User.Email;
import core.domain.User.Password;
import core.domain.User.Role;
import core.domain.User.User;
import core.persistence.PersistenceContext;
import dto.CaseTimeDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro
 */
public class AssignedCasesControllerTest {

    static CaseI instance1 = new CaseI(new CaseType("type"), CaseState.VALIDATING, new CaseCode("code1"), false, CasePriority.MAX, new CaseDate("05/06/06"));
    static CaseI instance2 = new CaseI(new CaseType("type"), CaseState.VALIDATING, new CaseCode("code2"), false, CasePriority.MAX, new CaseDate("06/06/06"));
    static CaseI instance3 = new CaseI(new CaseType("type"), CaseState.VALIDATING, new CaseCode("code3"), false, CasePriority.MAX, new CaseDate("04/06/06"));
    static User u= new User(new Email("ar2@gmail.com"),new Password("ar2") , Role.RISK_ANALIST);
    
    static LoginController lc = new LoginController();
    
    public AssignedCasesControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        PersistenceContext.repositories().UserRepository().save(u);
        lc.loginRiskAnalysis("ar2@gmail.com", "ar2");
        instance1.alterDate(CaseDate.now().toString());
        instance1.associateEmployee("himself",LoginController.logged.identity());
        instance2.alterDate(CaseDate.now().toString());
        instance2.associateEmployee("himself",LoginController.logged.identity());
        instance3.alterDate(CaseDate.now().toString());
        instance3.associateEmployee("himself",LoginController.logged.identity());
        PersistenceContext.repositories().caseRepository().save(instance1);
        PersistenceContext.repositories().caseRepository().save(instance2);
        PersistenceContext.repositories().caseRepository().save(instance3);
    }

    @AfterClass
    public static void tearDownClass() {
        PersistenceContext.repositories().caseRepository().delete(instance1);
        PersistenceContext.repositories().caseRepository().delete(instance2);
        PersistenceContext.repositories().caseRepository().delete(instance3);
        PersistenceContext.repositories().UserRepository().delete(u);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of listPendingCases method, of class AssignedCasesController.
     */
    @Test
    public void testListPendingCases() throws Exception {
        System.out.println("listPendingCases");
        CaseState s = CaseState.VALIDATING;
        AssignedCasesController instance = new AssignedCasesController();
        List<CaseTimeDTO> expResult = new ArrayList<>();
        List<String> listInsurances = new ArrayList<>();
        listInsurances.add("Casa");
        listInsurances.add("Telemóvel");
        CaseTimeDTO dto1 = new CaseTimeDTO("code3", "00:00:06", new ArrayList<>());
        CaseTimeDTO dto2 = new CaseTimeDTO("code1", "00:00:10", new ArrayList<>());
        CaseTimeDTO dto3 = new CaseTimeDTO("code2", "00:00:10", new ArrayList<>());
        //CaseTimeDTO dto4 = new CaseTimeDTO("Code1", "00:00:06", listInsurances);
        //CaseTimeDTO dto5 = new CaseTimeDTO("Code3", "00:00:06", listInsurances);
        expResult.add(dto1);
        expResult.add(dto2);
        expResult.add(dto3);
//        expResult.add(dto4);
//        expResult.add(dto5);
        List<CaseTimeDTO> result = instance.listPendingCases(s);
        assertEquals(expResult.get(0).CaseCode, result.get(0).CaseCode);
        assertEquals(expResult.get(0).listInsurances, result.get(0).listInsurances);
        assertEquals(expResult.get(1).CaseCode, result.get(1).CaseCode);
        assertEquals(expResult.get(1).listInsurances, result.get(1).listInsurances);
        assertEquals(expResult.get(2).CaseCode, result.get(2).CaseCode);
        assertEquals(expResult.get(2).listInsurances, result.get(2).listInsurances);
//        assertEquals(expResult.get(3).CaseCode, result.get(3).CaseCode);
//        assertEquals(expResult.get(3).listInsurances, result.get(3).listInsurances);
//        assertEquals(expResult.get(4).CaseCode, result.get(4).CaseCode);
//        assertEquals(expResult.get(4).listInsurances, result.get(4).listInsurances);
        
    }

}
