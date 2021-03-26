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
import core.domain.RiskMatrixs.Attributes.Weight;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class DefinedColumnBuilderTest {

   

    /**
     * Test of setWeight method, of class DefinedColumnBuilder.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        Weight weight = new Weight(1);
        DefinedColumnBuilder instance = new DefinedColumnBuilder();
        instance.setWeight(weight);
        assertNotNull(instance.weight);
    }

    /**
     * Test of setContribution method, of class DefinedColumnBuilder.
     */
    @Test
    public void testSetContribution() { 
        System.out.println("setContribution");
        Contribution contribution = Contribution.NEGATIVE;
        DefinedColumnBuilder instance = new DefinedColumnBuilder();
        instance.setContribution(contribution);
        assertNotNull(instance.contribution);
    }

    /**
     * Test of setNeed method, of class DefinedColumnBuilder.
     */
    @Test
    public void testSetNeed() {
        System.out.println("setNeed");
        NeedOfAnalisys need = NeedOfAnalisys.FACULTATIVE;
        DefinedColumnBuilder instance = new DefinedColumnBuilder();
        instance.setNeed(need);
        assertNotNull(instance.need);
    }

    /**
     * Test of validate method, of class DefinedColumnBuilder.
     */
    @Test
    public void testValidateAndBuild() {
        System.out.println("validate");
        DefinedColumnBuilder instance = new DefinedColumnBuilder();
        boolean result = instance.validate();
        assertFalse(instance.validate());
        instance.setRiskFactor(new RiskFactor(Metric.DISTANCIA, new STName("name")));
        assertFalse(instance.validate());
        instance.setWeight(new Weight(2));
        assertFalse(instance.validate());
        instance.setContribution(Contribution.POSITIVE);
        assertFalse(instance.validate());
        instance.setNeed(NeedOfAnalisys.OBLIGATORY);
        assertTrue(instance.validate());
        assertNotNull(instance.build());
        instance.weight = null;
        assertNull(instance.build());

    }

    /**
     * Test of buildDefault method, of class DefinedColumnBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildDefault() {
        System.out.println("buildDefault");
        RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("name"));
        DefinedColumnBuilder instance = new DefinedColumnBuilder();
        DefinedColumn result = instance.defaultBuilder(risk).build();
        assertNotNull(result);
        instance.defaultBuilder(null);
    }

}
