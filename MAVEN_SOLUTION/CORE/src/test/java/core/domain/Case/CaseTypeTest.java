/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

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
public class CaseTypeTest {

    public CaseTypeTest() {
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
     * Test of obtainType method, of class CaseType.
     */
    @Test
    public void testObtainType() {
        System.out.println("obtainType");
        CaseType instance = new CaseType("tipoTest");
        String expResult = "tipoTest";
        String result = instance.obtainType();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class CaseType.
     */
    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        Object obj = null;
        CaseType instance = new CaseType();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        Object obj = new CaseType("type");
        CaseType instance = new CaseType("type");
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class CaseType.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        CaseType instance = new CaseType("testType");
        int expResult = hashCode(instance);
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    public int hashCode(CaseType type) {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(type.obtainType());
        return hash;
    }

    /**
     * Test of toString method, of class CaseType.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CaseType instance = new CaseType("testType");
        String expResult = "Type: " + instance.obtainType();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
