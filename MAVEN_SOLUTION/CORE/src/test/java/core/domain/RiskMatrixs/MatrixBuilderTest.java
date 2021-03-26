/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.RiskMatrixs;

import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.MetricStrategy;
import core.domain.RiskFactors.RiskFactor;
import core.domain.Surrounding.STName;
import core.domain.RiskMatrixs.Attributes.Contribution;
import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.RiskMatrixs.Attributes.Weight;
import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class MatrixBuilderTest {

    RiskFactor risk = new RiskFactor(Metric.DISTANCIA, new STName("Sea"));
    Coverage cov = new Coverage(new CoverageName("TEST"));
    Weight weight = new Weight(10);
    Contribution contribution = Contribution.POSITIVE;
    NeedOfAnalisys need = NeedOfAnalisys.OBLIGATORY;
    RiskLevel lowRisk = new RiskLevel(1);
    RiskLevel medRisk = new RiskLevel(4);
    RiskLevel hiRisk = new RiskLevel(6);
    Scale scale = new Scale(lowRisk, medRisk, hiRisk);

    /**
     * Test of changeState method, of class MatrixBuilder.
     */
    @Test
    public void testChangeState_AllVariants() {
        System.out.println("testChangeState_AllVariants");
        MatrixBuilder.State state = MatrixBuilder.State.BASE;
        MatrixBuilder instance = new MatrixBuilder(state);
        assertEquals(MatrixBuilder.State.BASE, instance.getState());
        instance.changeState(MatrixBuilder.State.DEFINED);
        assertEquals(MatrixBuilder.State.DEFINED, instance.getState());
        instance.changeState(MatrixBuilder.State.DETAILED);
        assertEquals(MatrixBuilder.State.DETAILED, instance.getState());
        instance.changeState(MatrixBuilder.State.BASE);
        assertEquals(MatrixBuilder.State.BASE, instance.getState());
    }

    /**
     * Test of addColumnBuilder method, of class MatrixBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAdd_GetColumnBuilder() {
        System.out.println("testAdd_GetColumnBuilder");
        MatrixBuilder builder = new MatrixBuilder(MatrixBuilder.State.BASE);
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        builder.addColumnBuilder(cov, cb);
        assertEquals(builder.getColumnBuilder(cov, risk), cb);
        builder.addColumnBuilder(cov, ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE));

    }

    /**
     * Test when a coverage does not have specified the same amount unique risks
     * that line does.
     */
    @Test
    public void testValidate_differentAmountOfRisks() {
        System.out.println("testValidate_differentAmountOfRisks");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate());
    }

    /**
     * Tests when a coverage has the same amount of risks as the line but there
     * are at least one different Risk in each.
     */
    @Test
    public void testValidate_missingCoverageRisks() {
        System.out.println("testValidate_missingCoverageRisks");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(new RiskFactor(Metric.QUANTIDADE, new STName("Sea")));
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate());
    }

    /**
     * Tests when at least one column is invalid.
     */
    @Test
    public void testValidate_InvalidColumn() {
        System.out.println("testValidate_InvalidColumn");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.DETAILED, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate());
    }

    /**
     * Tests when the validation is successful.
     */
    @Test
    public void testValidate_ensureSuccess() {
        System.out.println("testValidate_ensureSuccess");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertTrue(instance.validate());
    }

    /**
     * Test if the BaseColumn builders' subclass passes, regardless of wether
     * they are valid for their state- tests if a builder is valid the Base
     * state.
     */
    @Test
    public void testValidateColumn_ensureBaseSubclassesPass() {
        System.out.println("testValidateColumn_ensureBaseSubclassesPass");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertTrue(instance.validate());
        instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertTrue(instance.validate());

        instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertTrue(instance.validate());
    }

    /**
     * Tests if a BaseColumnBuilder can pass the validation of its subclasses
     * state
     */
    @Test
    public void testValidateColumn_ensureBaseBuilderFailsWhenStateIsHigher() {
        System.out.println("testValidateColumn_ensureBaseBuilderFailsWhenStateIsHigher");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.DEFINED, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate());

        instance = new MatrixBuilder(MatrixBuilder.State.DETAILED, MatrixVersion.create("TestVersion1"));
        cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate()); 

    }

    /**
     * Test if the DefineColumn builders' subclass passes, regardless of wether
     * they are valid for their state- tests if a builder is valid the Defined
     * state.
     */
    @Test
    public void testValidateColumn_ensureDefinedSubclassesPass() {
        System.out.println("testValidateColumn_ensureDefinedSubclassesPass");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        DefinedColumnBuilder cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
        cb.setRiskFactor(risk);
        cb.setContribution(contribution);
        cb.setNeed(need);
        cb.setWeight(weight);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertTrue(instance.validate());

        instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DETAILED);
        cb.setRiskFactor(risk);
        cb.setContribution(contribution);
        cb.setNeed(need);
        cb.setWeight(weight);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertTrue(instance.validate());

    }

    /**
     * Test if the DefineColumn builders' subclass passes, regardless of wether
     * they are valid for their state- tests if a builder is valid the Defined
     * state.
     */
    @Test
    public void testValidateColumn_ensureDefinedBuilderFailsWhenStateisHigher() {
        System.out.println("testValidateColumn_ensureDefinedBuilderFailsWhenStateisHigher");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.DETAILED, MatrixVersion.create("TestVersion1"));
        DefinedColumnBuilder cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        cb.setContribution(contribution);
        cb.setNeed(need);
        cb.setWeight(weight);
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate());
    }

    /**
     * Test of fillByDefault method, of class MatrixBuilder.
     */
    @Test
    public void testFillByDefault() {
        System.out.println("fillByDefault");

        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.DEFINED, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        cov.associateRisk(new RiskFactor(Metric.DISTANCIA, new STName("Sea")));
        instance.addColumnBuilder(cov, cb);
        assertFalse(instance.validate());
        instance.fillByDefault();
        //FIXME BuildByDefault error
        //assertTrue(instance.validate());
    }

    /**
     * Test of getVersion method, of class MatrixBuilder.
     */
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TEst1"));
        MatrixVersion expResult = MatrixVersion.create("TEst1");
        MatrixVersion result = instance.getVersion();
        assertEquals(expResult, result);
    }

     /**
     * Test of build method, of class MatrixBuilder.
     */
    @Test
    public void testBuild_fail() {
        System.out.println("testBuild_fail");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        instance.addColumnBuilder(cov, cb);
        assertNull(instance.build());
    }

    /**
     * Test of build method, of class MatrixBuilder.
     */
    @Test
    public void testBuild_sucess() {
        System.out.println("testBuild_sucess");
        MatrixBuilder instance = new MatrixBuilder(MatrixBuilder.State.BASE, MatrixVersion.create("TestVersion1"));
        ColumnBuilder cb = ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE);
        cb.setRiskFactor(risk);
        cov.associateRisk(risk);
        instance.addColumnBuilder(cov, cb);
        assertNotNull(instance.build());
    }

}
