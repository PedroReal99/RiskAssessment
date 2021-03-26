/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Coverage;

import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
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
public class CoverageTest {

    public CoverageTest() {
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
     * Test of getId method, of class Coverage.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Coverage instance = new Coverage(new CoverageName("Incendios"));
        CoverageName expResult = new CoverageName("Incendios");
        CoverageName result = instance.identity();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Coverage.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Coverage instance = new Coverage(new CoverageName("Incendios"));
        String expResult = "Incendios";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Coverage.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Coverage(new CoverageName("Incendios"));
        Coverage instance = new Coverage(new CoverageName("Incendios"));
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);

        Coverage instance1 = new Coverage(new CoverageName("I"));
        boolean result1 = instance1.equals(obj);
        assertEquals(false, result1);
    }

}
