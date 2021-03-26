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
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class DetailedColumnTest {

    RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("Sea"));
    Weight weight = new Weight(10);
    Contribution contribution = Contribution.POSITIVE;
    NeedOfAnalisys need = NeedOfAnalisys.OBLIGATORY;
    RiskLevel lowRisk = new RiskLevel(1);
    RiskLevel medRisk = new RiskLevel(4);
    RiskLevel hiRisk = new RiskLevel(6);
    Scale scale = new Scale(lowRisk, medRisk, hiRisk);

    /**
     * Test of changeScale method, of class DetailedColumn.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeScale() {
        System.out.println("changeScale");
        DetailedColumn instance = new DetailedColumn(risk, weight, contribution, need, scale);
        assertEquals(instance.obtainScale(), scale);
        instance.changeScale(new Scale(hiRisk, medRisk, lowRisk));
        assertEquals(instance.obtainScale(), new Scale(hiRisk, medRisk, lowRisk));
        instance.changeScale(null);
    }

    /**
     * Test of obtainScale method, of class DetailedColumn.
     */
    @Test
    public void testObtainScale() {
        System.out.println("obtainScale");
        DetailedColumn instance = new DetailedColumn(risk, weight, contribution, need, scale);
        Scale expResult = scale;
        Scale result = instance.obtainScale();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class DetailedColumn.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        DetailedColumn instance = new DetailedColumn(risk, weight, contribution, need, scale);
        String expResult = "Defined Column: " + risk.toString()
                + "|W " + weight
                + "|C " + contribution
                + "|N " + need
                + "\n" + scale;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
