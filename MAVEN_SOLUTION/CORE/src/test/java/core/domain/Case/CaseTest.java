/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import core.domain.location.GPSLocation;
import core.domain.location.PostLocation;
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
 * @author morei
 */
public class CaseTest {

    CaseI instance1 = new CaseI(new CaseType("type"), new CaseState("state"), new CaseCode("code"), false, CasePriority.MAX, new CaseDate("06/06/06"));

    public CaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAssociatedRiskFactors method, of class CaseI.
     */
    @Test
    public void testGetAssociatedRiskFactors() {
        System.out.println("getAssociatedRiskFactors");
        List<InsuranceObject> expResult = new ArrayList<>();
        InsuranceObject i = new InsuranceObject(new InsuranceName("name"), new PostLocation());
        expResult.add(i);
        CaseI instance = new CaseI(new CaseType("type"), new CaseState("state"), new CaseCode("code"), expResult, false, CasePriority.MAX,new CaseDate("06/06/06"));

        List<InsuranceObject> result = instance.getAssociatedInsuranceObjects();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of toString method, of class CaseI.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CaseI instance = new CaseI(new CaseType("type"), new CaseState("state"), new CaseCode("code"), false, CasePriority.MAX,new CaseDate("06/06/06"));
        String expResult = "Type: Type: typeState: + State: stateCode: code Date:06/06/06";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class CaseI.
     */
    @Test
    public void testEqualsNull() {
        System.out.println("equals");
        Object obj = null;
        CaseI instance = new CaseI();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        Object obj = new CaseI(new CaseType("type"), new CaseState("state"), new CaseCode("code"), false, CasePriority.MAX,new CaseDate("06/06/06"));
        boolean expResult = true;
        boolean result = instance1.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainType method, of class CaseI.
     */
    @Test
    public void testObtainType() {
        System.out.println("obtainType");
        String expResult = "Type: type";
        String result = instance1.obtainType();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainState method, of class CaseI.
     */
    @Test
    public void testObtainState() {
        System.out.println("obtainState");
        CaseI instance = new CaseI();
        String expResult = "State: state";
        String result = instance1.obtainState();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainCode method, of class CaseI.
     */
    @Test
    public void testObtainCode() {
        System.out.println("obtainCode");
        CaseI instance = new CaseI();
        String expResult = "code";
        String result = instance1.obtainCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of sameAs method, of class CaseI.
     */
    @Test
    public void testSameAs() {
        System.out.println("sameAs");
        Object other = instance1;
        boolean expResult = true;
        boolean result = instance1.sameAs(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of identity method, of class CaseI.
     */
    @Test
    public void testIdentity() {
        System.out.println("identity");
        CaseI instance = new CaseI();
        CaseCode expResult = new CaseCode("code");
        CaseCode result = instance1.identity();
        assertEquals(expResult, result);
    }

}
