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
public class ScaleTest {

    Scale s = new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5));

    public ScaleTest() {
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
        hash = 29 * hash + Objects.hashCode(s.obtainScale());
        return hash;
    }

    @Test
    public void testHashCode() {
        assertEquals(s.hashCode(), hashCodeCalculator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void endureRiskLevelsAreNotNull() {
        assertNotNull(new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5)));
        new Scale(null, null, null);
    }

    /**
     * Test of equals method, of class Scale.
     */
    @Test
    public void testEquals() {
        Object obj = new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5));
        assertTrue(s.equals(s));
        assertFalse(s.equals(null));
        assertTrue(s.equals(obj));
        obj = new Scale(new RiskLevel(2), new RiskLevel(3), new RiskLevel(5));
        assertFalse(s.equals(obj));
        obj = new Object();
        assertFalse(s.equals(obj));
    }

    /**
     * Test of toString method, of class Scale.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Scale "
                + "\nLow  : 1"
                + "\nMed  : 3"
                + "\nHigh : 5"
                + "\n";
        String result = s.toString();
        assertEquals(expResult, result);

    }

    /**
     * Test of obtainRiskLevelValue method, of class Scale.
     */
    @Test
    public void testObtainRiskLevelValue() {
        System.out.println("obtainRiskLevelValue");
        Designation d = new Designation("HIGH");
        int expResult = 5;
        int result = s.obtainRiskLevelValue(d);
        assertEquals(expResult, result);
    }

}
