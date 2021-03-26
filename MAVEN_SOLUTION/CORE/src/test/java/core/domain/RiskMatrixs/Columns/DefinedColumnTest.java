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
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.Weight;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class DefinedColumnTest {
    
    RiskFactor risk =new RiskFactor(Metric.DISTANCIA, new STName("Sea"));
    Weight weight =new Weight(10);
    Contribution contribution=Contribution.POSITIVE;
    NeedOfAnalisys need= NeedOfAnalisys.OBLIGATORY;
    
    /**
     * Test of changeNecessity method, of class DefinedColumn.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeNecessity() {
        System.out.println("changeNecessity");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        assertEquals(instance.obtainNecessity(),need);
        instance.changeNecessity(NeedOfAnalisys.FACULTATIVE);
        assertEquals(instance.obtainNecessity(),NeedOfAnalisys.FACULTATIVE);
        instance.changeNecessity(null);
    }

    /**
     * Test of changeWeight method, of class DefinedColumn.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeWeight() {
        System.out.println("changeWeight");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        assertEquals(instance.obtainWeight(),weight);
        instance.changeWeight(new Weight(9));
        assertEquals(instance.obtainWeight(),new Weight(9));
        instance.changeWeight(null);
    }

    /**
     * Test of changeContribution method, of class DefinedColumn.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeContribution() {
        System.out.println("changeContribution");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        assertEquals(instance.obtainContribution(),Contribution.POSITIVE);
        instance.changeContribution(Contribution.NEGATIVE);
        assertEquals(instance.obtainContribution(),Contribution.NEGATIVE);
        instance.changeContribution(null);
    }

    /**
     * Test of obtainWeight method, of class DefinedColumn.
     */
    @Test
    public void testObtainWeight() {
        System.out.println("obtainWeight");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        Weight expResult = weight;
        Weight result = instance.obtainWeight();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of obtainNecessity method, of class DefinedColumn.
     */
    @Test
    public void testObtainNecessity() {
        System.out.println("obtainNecessity");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        NeedOfAnalisys expResult = need;
        NeedOfAnalisys result = instance.obtainNecessity();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainContribution method, of class DefinedColumn.
     */
    @Test
    public void testObtainContribution() {
        System.out.println("obtainContribution");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        Contribution expResult = contribution;
        Contribution result = instance.obtainContribution();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainRiskFactor method, of class DefinedColumn.
     */
    @Test
    public void testObtainRiskFactor() {
        System.out.println("obtainRiskFactor");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        RiskFactor expResult = risk;
        RiskFactor result = instance.obtainRiskFactor();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class DefinedColumn.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        DefinedColumn instance = new DefinedColumn(risk, weight, contribution, need);
        String expResult = "Defined Column: " +risk.toString()
                + "|W " + weight
                + "|C " +contribution
                +"|N "+ need;
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
