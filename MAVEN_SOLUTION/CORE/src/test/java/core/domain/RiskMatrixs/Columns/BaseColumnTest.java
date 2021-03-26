/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.MetricStrategy;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class BaseColumnTest {

    RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("Test"));
    public BaseColumnTest() {
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
     * Test of obtainRiskFacotr method, of class BaseColumn.
     */
    @Test
    public void testObtainRiskFacotr() {
        System.out.println("obtainRiskFacotr");
        BaseColumn instance = new BaseColumn();
        RiskFactor expResult = null;
        RiskFactor result = instance.obtainRiskFacotr();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class BaseColumn.
     */
    @Test
    public void testHashCode() {
        
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(risk);
        assertEquals(hash, new BaseColumn(risk).hashCode());
    }

    /**
     * Test of equals method, of class BaseColumn.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        BaseColumn instance = new BaseColumn();
        assertTrue(instance.equals(instance));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals("TEST"));
        assertTrue(instance.equals(new BaseColumn()));
    }

    /**
     * Test of toString method, of class BaseColumn.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BaseColumn instance = new BaseColumn(risk);
        String expResult = "Base column: "+risk.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of sameAs method, of class BaseColumn.
     */
    @Test
    public void testSameAs() {
        System.out.println("sameAs");
        Object other = null;
        BaseColumn instance = new BaseColumn();
        boolean expResult = false;
        boolean result = instance.sameAs(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of identity method, of class BaseColumn.
     */
    @Test
    public void testIdentity() {
        System.out.println("identity");
        BaseColumn instance = new BaseColumn(risk);
        RiskFactor expResult = risk;
        RiskFactor result = instance.identity();
        assertEquals(expResult, result);
    }

}
