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
import core.domain.Insurance.InsuranceObject;
import core.domain.User.*;
import core.persistence.PersistenceContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author morei
 */
public class CaseControllerTest {

    List<Email> e = new ArrayList<>();

    static CaseI instance1 = new CaseI(new CaseType("type"), CaseState.WAITING, new CaseCode("code1"), false, CasePriority.MAX, new CaseDate("2005-06-06 00:00:00"));
    static CaseI instance2 = new CaseI(new CaseType("type"), CaseState.WAITING, new CaseCode("code2"), false, CasePriority.MAX, new CaseDate("2006-06-06 00:00:00"));
    static CaseI instance3 = new CaseI(new CaseType("type"), CaseState.WAITING, new CaseCode("code3"), false, CasePriority.MAX, new CaseDate("2004-06-06 00:00:00"));
    static CaseI instance4 = new CaseI(new CaseType("type"), CaseState.PROCESSED, new CaseCode("code4"), false, CasePriority.MAX, new CaseDate("2004-06-07 00:00:00"));
    static CaseI instance5 = new CaseI(new CaseType("type"), CaseState.PROCESSED, new CaseCode("code5"), false, CasePriority.MAX, new CaseDate("2004-06-06 00:00:00"));
    static CaseI instance6 = new CaseI(new CaseType("type"), CaseState.PROCESSED, new CaseCode("code6"), false, CasePriority.MAX, new CaseDate("2003-06-05 00:00:00"));
    static CaseI instance7 = new CaseI(new CaseType("type"), CaseState.VALIDATING, new CaseCode("code7"), false, CasePriority.MAX, new CaseDate("2003-6-06 00:00:00"));
    static CaseI instance8 = new CaseI(new CaseType("type"), CaseState.PROCESSING, new CaseCode("code8"), false, CasePriority.MAX, new CaseDate("2003-6-06 00:00:00"));

    static User user = new User(new Email("1170570@gmail.com"), new Password("ola"), Role.RISK_ANALIST);
    static User admin = new User(new Email("admin1@gmail.com"), new Password("admin1"), Role.ADMIN);

    public CaseControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance4.associateEmployee("himself",user.obtainEmail());
        instance4.validatedDate(new CaseDate("2019-06-16 00:00:00"));
        instance4.validationStartDate(new CaseDate("2019-06-15 00:00:00"));
        instance5.associateEmployee("himself",user.obtainEmail());
        instance5.validatedDate(new CaseDate("2019-06-17 00:00:00"));
        instance5.validationStartDate(new CaseDate("2019-06-15 00:00:00"));
        instance6.associateEmployee("himself",user.obtainEmail());
        instance6.validatedDate(new CaseDate("2019-06-18 00:00:00"));
        instance6.validationStartDate(new CaseDate("2019-06-15 00:00:00"));
        instance7.associateEmployee("himself",user.obtainEmail());

        PersistenceContext.repositories().UserRepository().save(user);
        PersistenceContext.repositories().UserRepository().save(admin);
        PersistenceContext.repositories().caseRepository().save(instance1);
        PersistenceContext.repositories().caseRepository().save(instance2);
        PersistenceContext.repositories().caseRepository().save(instance3);
        PersistenceContext.repositories().caseRepository().save(instance4);
        PersistenceContext.repositories().caseRepository().save(instance5);
        PersistenceContext.repositories().caseRepository().save(instance6);
        PersistenceContext.repositories().caseRepository().save(instance7);
        PersistenceContext.repositories().caseRepository().save(instance8);
    }

    @AfterClass
    public static void tearDownClass() {
        PersistenceContext.repositories().caseRepository().delete(instance1);
        PersistenceContext.repositories().caseRepository().delete(instance2);
        PersistenceContext.repositories().caseRepository().delete(instance3);
        PersistenceContext.repositories().caseRepository().delete(instance4);
        PersistenceContext.repositories().caseRepository().delete(instance5);
        PersistenceContext.repositories().caseRepository().delete(instance6);
        PersistenceContext.repositories().caseRepository().delete(instance7);
        PersistenceContext.repositories().caseRepository().delete(instance8);
        PersistenceContext.repositories().UserRepository().delete(user);

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of listCasePending method, of class CaseController.
     */
    @Ignore
    public void testListCasePending() {
        System.out.println("listCasePending");
        CaseController instance = new CaseController();
        List<String> expResult = new ArrayList<>();
        expResult.add(instance3.obtainCode());
        expResult.add(instance1.obtainCode());
        Iterable<CaseI> result = instance.listCaseByStateWithoutEmployee(CaseState.WAITING);
        Iterator<CaseI> r = result.iterator();
        assertEquals(expResult.get(0), r.next().obtainCode());
        assertEquals(expResult.get(1), r.next().obtainCode());
    }

    @Test
    public void testChangeCaseAr() {
        System.out.println("testChangeCaseAr");
        LoginController lc = new LoginController();
        lc.loginRiskAnalysis("admin1@gmail.com", "admin1");
        assertNull(instance2.getEmployee());
        CaseController.changeCaseAr(new CaseCode("code2"));
        CaseI caseI = PersistenceContext.repositories().caseRepository().findByCaseCode(new CaseCode("code2"));
        assertEquals(new Email("admin1@gmail.com"), caseI.getEmployee());
    }

    /**
     * Test of listCaseByStateWithDistrict method, of class CaseController.
     */
    @Ignore
    public void testListCaseByStateWithDistrict() {
        System.out.println("listCaseByStateWithDistrict");
        CaseState state = new CaseState("code2");
        String district = "porto";
        CaseController instance = new CaseController();
        List<CaseI> expResult = null;
        List<CaseI> result = instance.listCaseByStateWithDistrict("code2", district);
        assertEquals(expResult, result);
    }

    @Test
    public void listCasesValidatedByAR() {
        System.out.println("listCasesValidatedByAR");
        CaseController instance = new CaseController();
        LoginController lc = new LoginController();
        lc.loginRiskAnalysis("1170570@gmail.com", "ola");
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance6);
        expResult.add(instance5);
        expResult.add(instance4);
        List<CaseI> result = instance.listCasesValidatedByAR(null);
        assertEquals(expResult, result);
    }

    @Test
    public void listCasesValidatedByARFilterByTime() {
        System.out.println("listCasesValidatedByARFilterByTime");
        CaseController instance = new CaseController();
        LoginController lc = new LoginController();
        lc.loginRiskAnalysis("1170570@gmail.com", "ola");
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance5);
        expResult.add(instance4);
        List<CaseDate> list = new ArrayList<>();
        CaseDate date1 = new CaseDate("2004-01-01 00:00:00");
        CaseDate date2 = new CaseDate("2006-01-01 00:00:00");
        list.add(date1);
        list.add(date2);
        List<CaseI> result = instance.listCasesValidatedByAR(list);
        assertEquals(expResult, result);
    }

    @Test
    public void makeFormatedString() {
        System.out.println("makeFormatedString");
        CaseController instance = new CaseController();
        List<CaseI> list = new ArrayList<>();
        list.add(instance6);
        list.add(instance5);
        list.add(instance4);
        String result = instance.makeFormatedString(list);
        String expResult = "Case id: code6 tempo decorrido: 3 days 0 hours 0 minutes and 0 seconds.\nCase id: code5 tempo decorrido: 2 days 0 hours 0 minutes and 0 seconds.\nCase id: code4 tempo decorrido: 1 days 0 hours 0 minutes and 0 seconds.\n";
        assertEquals(expResult, result);
    }

    @Test
    public void buildSummary() {
        System.out.println("buildSummaryTest");
        LoginController lc = new LoginController();
        lc.loginRiskAnalysis("1170570@gmail.com", "ola");
        CaseController instance = new CaseController();
        List<CaseI> list = instance.listCasesValidatedByAR(null);
        instance.makeFormatedString(list);
        String result = instance.buildSummary();
        String expResult = "email= 1170570@gmail.com validated 3 Cases and took an average of 2 days 0 hours 0 minutes and 0 seconds.";
        assertEquals(expResult, result);
    }

    @Test
    public void testTransformIntoXML() {
        System.out.println("transformIntoXML");
        LoginController lc = new LoginController();
        lc.loginRiskAnalysis("1170570@gmail.com", "ola");
        CaseController instance = new CaseController();
        List<CaseI> list = instance.listCasesValidatedByAR(null);
        String output = instance.makeFormatedString(list);
        String summary = instance.buildSummary();
        File expResult = new File("ar05XHTMLTest.xhtml");
        File result = instance.convertToXHTML(output, summary, "ar05XHTMLTest2");
        assertEquals(expResult.getFreeSpace(), result.getFreeSpace());
    }


}
