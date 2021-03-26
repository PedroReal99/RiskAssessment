/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

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
public class CalculationDetailTest {

    public CalculationDetailTest() {
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
     * Test of obtainDetails method, of class CalculationDetail.
     */
    @Test
    public void testObtainDetails() {
        System.out.println("obtainDetails");
        List<String> temp = new ArrayList<>();
        CalculationDetail instance = new CalculationDetail(temp);
        List<String> expResult = new ArrayList<>();
        expResult.add("detalhes");
        instance.addDetailsOfCalculation("detalhes");
        List<String> result = instance.obtainDetails();
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of toString method, of class CalculationDetail.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        List<String> temp = new ArrayList<>();
        CalculationDetail instance = new CalculationDetail(temp);
        instance.addDetailsOfCalculation("detalhes");
        String expResult = "Calculation Details: " + instance.obtainDetails();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class CalculationDetail.
     */
    @Test
    public void testEqualsNull() {
        System.out.println("equals");
        Object obj = null;
        List<String> temp = new ArrayList<>();
        CalculationDetail instance = new CalculationDetail(temp);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        Object obj = new CalculationDetail();
        List<String> temp = new ArrayList<>();
        CalculationDetail instance = new CalculationDetail(temp);
        boolean expResult = false;
        instance.addDetailsOfCalculation("falso");
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualstrue() {
        System.out.println("equals");
        List<String> temp = new ArrayList<>();
        CalculationDetail instance = new CalculationDetail(temp);
        Object obj = instance;
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

}
