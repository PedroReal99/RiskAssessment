/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import core.domain.Insurance.ClassificationTables.TableLine;
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
public class ClassificationTest {

    public ClassificationTest() {
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
     * Test of equals method, of class Classification.
     */
    @Test
    public void testEqualsNull() {
        System.out.println("equals");
        Object obj = null;
        Classification instance = new Classification();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        Object obj = new RiskIndex();
        Classification instance = new Classification();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        Classification instance = new Classification();
        Object obj = instance;

        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
}
