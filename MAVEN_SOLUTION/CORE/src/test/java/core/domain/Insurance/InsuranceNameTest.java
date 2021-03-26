/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import core.domain.Case.CaseCode;
import java.util.Objects;
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
public class InsuranceNameTest {

    InsuranceName i = new InsuranceName("name");

    public InsuranceNameTest() {
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
     * Test of toString method, of class InsuranceName.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        InsuranceName instance = new InsuranceName("name");
        String expResult = "name";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class InsuranceName.
     */
    @Test
    public void testEqualsNull() {
        System.out.println("equals");
        Object obj = null;
        InsuranceName instance = new InsuranceName();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        Object obj = new CaseCode("code");
        InsuranceName instance = new InsuranceName();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void ensureInsuranceNameIsValid() {
        new InsuranceName("name");
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsuranceDoubleSpaceBetweenWordsNotAllowed() {
//        new InsuranceName("sdwaef  dwafes45");
//    }

//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsuranceOnlyOneSpacesNotAllowed() {
//        new InsuranceName(" ");
//    }

//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsuranceOnlyMultipleSpacesNotAllowed() {
//        new InsuranceName("     ");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsuranceEmptyStringNotAllowed() {
//        new InsuranceName("");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsereIllegalCharactersNotAllowed() {
//        new InsuranceName("^~~");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsureIllegalCharactersBetweenWordNotAllowed() {
//        new InsuranceName("Dista^ncia");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void ensureInsureIllegalCharactersAfterWordAndSpaceNotAllowed() {
//        new InsuranceName("Distancia ^");
//    }

}
