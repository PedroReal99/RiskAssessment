/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import core.domain.RiskFactors.MetricStrategy;
import java.util.Objects;

/**
 *
 * @author CarloS
 */
public class ColumnBuilderTest {

    RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("Test"));

    public ColumnBuilderTest() {
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
     * Test of newColumnBuilder method, of class ColumnBuilder.
     */
    @Test
    public void testNewColumnBuilder() {
        System.out.println("newColumnBuilder"); 
        ColumnBuilder.State state = ColumnBuilder.State.BASE;
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(state);
        assertTrue(cb instanceof ColumnBuilder);
        assertFalse(cb instanceof DefinedColumnBuilder);

        state = ColumnBuilder.State.DEFINED;
        cb = ColumnBuilder.newColumnBuilder(state);
        assertTrue(cb instanceof DefinedColumnBuilder);
        assertFalse(cb instanceof DetailedColumnBuilder);

        state = ColumnBuilder.State.DETAILED;
        cb = ColumnBuilder.newColumnBuilder(state);
        assertTrue(cb instanceof DetailedColumnBuilder);
    }

    /**
     * Test of setRiskFactor method, of class ColumnBuilder.
     */
    @Test
    public void testSetRiskFactor() {
        System.out.println("setRiskFactor");
        ColumnBuilder instance = new ColumnBuilder();
        instance.setRiskFactor(risk);

        assertNotNull(instance.risk);
    }

    /**
     * Test of validate method, of class ColumnBuilder.
     */
    @Test
    public void testValidate() {
        System.out.println("validate");
        ColumnBuilder instance = new ColumnBuilder();
        boolean expResult = false;
        boolean result = instance.validate();
        assertEquals(expResult, result);
        instance.setRiskFactor(risk);
        assertTrue(instance.validate());
    }

    /**
     * Test of build method, of class ColumnBuilder.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        ColumnBuilder instance = new ColumnBuilder();
        BaseColumn result = instance.build();
        assertNull(result);

        RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("Test"));
        instance.setRiskFactor(risk);
        result = instance.build();
        assertNotNull(result);
    }

    /**
     * Test of buildDefault method, of class ColumnBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildDefault() {
        System.out.println("buildDefault");
        ColumnBuilder instance = new ColumnBuilder();
        BaseColumn result = instance.defaultBuilder(new RiskFactor(Metric.DISTANCIA, new STName("Test"))).build();
        assertNotNull(result);

        instance.defaultBuilder(null);
    }

    /**
     * Test of getRisk method, of class ColumnBuilder.
     */
    @Test
    public void testGetRisk() {
        System.out.println("getRisk");
        ColumnBuilder instance = new ColumnBuilder(risk);
        RiskFactor expResult = risk;
        RiskFactor result = instance.getRisk();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class ColumnBuilder.
     */
    @Test
    public void testHashCode() {

        System.out.println("hashCode");
        ColumnBuilder instance = new ColumnBuilder(risk);
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(risk);
        int result = instance.hashCode();
        assertEquals(hash, result);
    }

    /**
     * Test of equals method, of class ColumnBuilder.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        ColumnBuilder instance = new ColumnBuilder(risk);
        assertTrue(instance.equals(instance));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(new ColumnBuilder()));
        assertTrue(instance.equals(new ColumnBuilder(risk)));

    }

}
