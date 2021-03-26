/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

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
public class CaseStateTest {
    
   
    
    public CaseStateTest() {
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
     * Test of toString method, of class CaseState.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CaseState instance = new CaseState("Espera");
        String expResult = "State: Espera";
        String result = instance.toString();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class CaseState.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        CaseState instance = new CaseState();
        int expResult = 133;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class CaseState.
     */
    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        Object obj = new CaseState("Espera");;
        CaseState instance = new CaseState("Espera");
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of equals method, of class CaseState.
     */
    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        Object obj = new CaseState("Processado");;
        CaseState instance = new CaseState("Espera");
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
}
