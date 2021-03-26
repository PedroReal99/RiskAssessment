/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs.Columns;

import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
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
public class DetailedColumnBuilderTest {
    
    public DetailedColumnBuilderTest() {
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
     * Test of setScale method, of class DetailedColumnBuilder.
     */
    @Test
    public void testSetScale() {
        System.out.println("setScale");
        RiskLevel level=new RiskLevel(1);
        Scale scale = new Scale(level, level, level);
        DetailedColumnBuilder instance = new DetailedColumnBuilder();
        instance.setScale(scale);
        assertNotNull(instance.scale);
    }

    /**
     * Test of validate method, of class DetailedColumnBuilder.
     */
    @Test
    public void testValidate() {
        System.out.println("validate");
        DetailedColumnBuilder instance = new DetailedColumnBuilder();
        boolean result = instance.validate();
        instance.setRiskFactor(new RiskFactor(Metric.DISTANCIA, new STName("name")));
        instance.setWeight(new Weight(2));
        assertFalse(instance.validate());
        
        
        RiskLevel level=new RiskLevel(1);
        Scale scale = new Scale(level, level, level);
        instance.setScale(scale);
        assertFalse(instance.validate());  
        instance.setContribution(Contribution.POSITIVE);
        assertFalse(instance.validate());      
        instance.setNeed(NeedOfAnalisys.OBLIGATORY);
        assertTrue(instance.validate());
        assertNotNull(instance.build());
        instance.weight=null;
        assertNull(instance.build());
    }


    /**
     * Test of buildDefault method, of class DetailedColumnBuilder.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBuildDefault() {
        System.out.println("buildDefault");
        RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("name"));
        DetailedColumnBuilder instance = new DetailedColumnBuilder();
        DetailedColumn result = instance.defaultBuilder(risk).build();
        assertNotNull(result);
        risk=null;
        instance.defaultBuilder(risk);
    }
    
}
