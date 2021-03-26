/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Attributes;

import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasco_Rodrigues
 */
public class RiskLevelTest {
    
    RiskLevel r = new RiskLevel(8);
    
    public RiskLevelTest() {
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

    public int hashCodeCalculator() {
        int hash = 7;
        hash = 71 * hash + r.obtainRiskLevel();
        return hash;
    }
    
    @Test
    public void testHashCode() {
        assertEquals(r.hashCode(), hashCodeCalculator());
    }
    
    /**
     * Test of equals method, of class RiskLevel.
     */
    @Test
    public void testEquals() {
        Object obj = new RiskLevel(8);
        assertTrue(r.equals(r));
        assertFalse(r.equals(null));
        assertTrue(r.equals(obj));
        obj = new RiskLevel(7);
        assertFalse(r.equals(obj));
        obj = new Object();
        assertFalse(r.equals(obj));
    }

    /**
     * Test of toString method, of class RiskLevel.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        RiskLevel instance = new RiskLevel(8);
        String expResult = "8";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainRiskLevel method, of class RiskLevel.
     */
    @Test
    public void testObtainRiskLevel() {
        System.out.println("obtainRiskLevel");
        RiskLevel instance = new RiskLevel(8);
        int expResult = 8;
        int result = instance.obtainRiskLevel();
        assertEquals(expResult, result);
    }
    
}
