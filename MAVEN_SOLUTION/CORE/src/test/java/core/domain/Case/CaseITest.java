/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Case;

import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.Insurance.CalculationDetail;
import core.domain.Insurance.Classification;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.ClassificationTable;
import core.domain.Insurance.ClassificationTables.TableLine;
import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import core.domain.location.GPSLocation;
import core.domain.location.PostLocation;
import core.domain.Insurance.RiskIndex;
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
import core.domain.RiskMatrixs.Columns.DetailedColumnBuilder;
import core.domain.RiskMatrixs.MatrixBuilder;
import core.domain.RiskMatrixs.MatrixState;
import core.domain.RiskMatrixs.MatrixVersion;
import core.domain.RiskMatrixs.RiskMatrix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasco_Rodrigues
 */
public class CaseITest {
    
    RiskFactor RSK1 = new RiskFactor(Metric.DISTANCIA, new STName("Bombeiros"));
    RiskFactor RSK2 = new RiskFactor(Metric.QUANTIDADE, new STName("IE Saúde"));
    RiskFactor RSK3 = new RiskFactor(Metric.QUANTIDADE, new STName("Zona Urbana"));
    RiskFactor RSK4 = new RiskFactor(Metric.DISTANCIA, new STName("Policia"));
    RiskFactor RSK5 = new RiskFactor(Metric.QUANTIDADE, new STName("Médicos"));
    RiskFactor RSK6 = new RiskFactor(Metric.QUANTIDADE, new STName("Policias"));
    CaseCode ccc1 = new CaseCode("Caso 1");
    CaseType ct1 = new CaseType("Casa");
    CaseState cs1 = new CaseState("Á espera");
    CasePriority cp1 = CasePriority.MAX;
    Classification c1 = new Classification("HIGH");
    Classification c2 = new Classification("LOW");
    Classification c3 = new Classification("MEDIUM");
    Classification c4 = new Classification("(não detetado)");
    Classification c5 = new Classification("HIGH");
    Classification c6 = new Classification("(não detetado)");
    ClassificationColumn cc1 = new ClassificationColumn(RSK1,c1);
    ClassificationColumn cc2 = new ClassificationColumn(RSK2,c2);
    ClassificationColumn cc3 = new ClassificationColumn(RSK3,c3);
    ClassificationColumn cc4 = new ClassificationColumn(RSK4,c4);
    ClassificationColumn cc5 = new ClassificationColumn(RSK5,c5);
    ClassificationColumn cc6 = new ClassificationColumn(RSK6,c6);
    Coverage cvg1 = new Coverage(new CoverageName("Incendios"));
    Coverage cvg2 = new Coverage(new CoverageName("Tempestades"));
    Coverage cvg3 = new Coverage(new CoverageName("Tsunamis"));
    Scale sc1 = new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5));
    Scale sc2 = new Scale(new RiskLevel(2), new RiskLevel(4), new RiskLevel(6));
    Scale sc3 = new Scale(new RiskLevel(3), new RiskLevel(6), new RiskLevel(9));
    
    
    public CaseITest() {
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
     * Test of associateInsurance method, of class CaseI.
     */
    @Test
    public void testAssociateInsurance() {
        System.out.println("associateInsurance");
        CaseI instance = createCaseTest();
        InsuranceObject insuranceObject = instance.getAssociatedInsuranceObjects().get(0);
        instance.associateInsurance("",insuranceObject);
        assertTrue(instance.getAssociatedInsuranceObjects().contains(insuranceObject));
    }

    /**
     * Test of getAssociatedInsuranceObjects method, of class CaseI.
     */
    @Test
    public void testGetAssociatedInsuranceObjects() {
        System.out.println("getAssociatedInsuranceObjects");
        CaseI instance = createCaseTest();
        Map<RiskFactor,ClassificationColumn> map1test = new HashMap<>();  
        Map<RiskFactor,ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor,ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> expResult = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation(),new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation(),new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        expResult.add(ioteste);
        expResult.add(io2teste);
        List<InsuranceObject> result = instance.getAssociatedInsuranceObjects();
        assertEquals(expResult, result);
    }

    public int hashCodeCalculator() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(createCaseTest().getType());
        hash = 83 * hash + Objects.hashCode(createCaseTest().getState());
        hash = 83 * hash + Objects.hashCode(createCaseTest().identity());
        return hash;
    }
    
    @Test
    public void testHashCode() {
        assertEquals(createCaseTest().hashCode(), hashCodeCalculator());
    }

    /**
     * Test of obtainType method, of class CaseI.
     */
    @Test
    public void testObtainType() {
        System.out.println("obtainType");
        CaseI instance = createCaseTest();
        String expResult = "Type: Casa";
        String result = instance.obtainType();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainState method, of class CaseI.
     */
    @Test
    public void testObtainState() {
        System.out.println("obtainState");
        CaseI instance = createCaseTest();
        String expResult = "State: Á espera";
        String result = instance.obtainState();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainCode method, of class CaseI.
     */
    @Test
    public void testObtainCode() {
        System.out.println("obtainCode");
        CaseI instance = createCaseTest();
        String expResult = "Caso 1";
        String result = instance.obtainCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of sameAs method, of class CaseI.
     */
    @Test
    public void testSameAs() {
        System.out.println("sameAs");
        CaseI c = createCaseTest();
        Object obj = createCaseTest();
        assertTrue(c.sameAs(c));
        assertFalse(c.sameAs(null));
        assertTrue(c.sameAs(obj));
        obj = new CaseI();
        assertFalse(c.sameAs(obj));
        obj = new Object();
        assertFalse(c.sameAs(obj));
    }

    /**
     * Test of identity method, of class CaseI.
     */
    @Test
    public void testIdentity() {
        System.out.println("identity");
        CaseI instance = createCaseTest();
        CaseCode expResult = new CaseCode("Caso 1");
        CaseCode result = instance.identity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class CaseI.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        CaseI instance = createCaseTest();
        CaseType expResult = new CaseType("Casa");
        CaseType result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getState method, of class CaseI.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        CaseI instance = createCaseTest();
        CaseState expResult = new CaseState("Á espera");
        CaseState result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainTypeObject method, of class CaseI.
     */
    @Test
    public void testObtainTypeObject() {
        System.out.println("obtainTypeObject");
        CaseI instance = createCaseTest();
        CaseType expResult = new CaseType("Casa");
        CaseType result = instance.obtainTypeObject();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainStateObject method, of class CaseI.
     */
    @Test
    public void testObtainStateObject() {
        System.out.println("obtainStateObject");
        CaseI instance = createCaseTest();
        CaseState expResult = new CaseState("Á espera");
        CaseState result = instance.obtainStateObject();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateRiskAssessment method, of class CaseI.
     */
    //@Test
    public void testCalculateRiskAssessment() {
        System.out.println("calculateRiskAssessment");
        RiskMatrix rm = createMatrixTest();
        boolean det = false;
        CaseI instance = createCaseTest();
        boolean result = instance.calculateRiskAssessment("",rm, det);
        assertTrue(result);
    }

    /**
     * Test of alterCaseStatus method, of class CaseI.
     */
    @Test
    public void testAlterCaseStatus() {
        System.out.println("alterCaseStatus");
        String expected = "State: Processado";
        CaseI instance = createCaseTest();
        instance.alterCaseStatus("",CaseState.PROCESSED);
        assertEquals(expected, instance.getState().toString());
    }
    
    public CaseI createCaseTest() {
        Coverage cvg1 = new Coverage(new CoverageName("Incendios"));
        Coverage cvg2 = new Coverage(new CoverageName("Tempestades"));
        Coverage cvg3 = new Coverage(new CoverageName("Tsunamis"));
        Map<RiskFactor,ClassificationColumn> map1test = new HashMap<>();  
        Map<RiskFactor,ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor,ClassificationColumn> map3test = new HashMap<>();
        map1test.put(RSK1, cc1);
        map1test.put(RSK2, cc2);
        map2test.put(RSK3, cc3);
        map2test.put(RSK4, cc4);
        map3test.put(RSK5, cc5);
        map3test.put(RSK6, cc6);
        TableLine tb1test = new TableLine(map1test, cvg1);
        TableLine tb2test = new TableLine(map2test, cvg2);
        TableLine tb3test = new TableLine(map3test, cvg3);
        List<TableLine> lt = new ArrayList<>();
        lt.add(tb1test);
        lt.add(tb2test);
        lt.add(tb3test);
        ClassificationTable t1 = new ClassificationTable(lt);
        List<InsuranceObject> li = new ArrayList<>();
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation(),new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation(),new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, cs1, ccc1, li,false, cp1,new CaseDate("06/06/2006"));
        return c;
    }
    
        public RiskMatrix createMatrixTest() {
        ColumnBuilder builders[] = {ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
        };
        builders[0].setRiskFactor(RSK1);
        builders[1].setRiskFactor(RSK2);
        builders[2].setRiskFactor(RSK3);
        builders[3].setRiskFactor(RSK4);
        builders[4].setRiskFactor(RSK5);
        builders[5].setRiskFactor(RSK6);
        cvg1.associateRisk(RSK1);
        cvg1.associateRisk(RSK2);
        cvg2.associateRisk(RSK3);
        cvg2.associateRisk(RSK4);
        cvg3.associateRisk(RSK5);
        cvg3.associateRisk(RSK6);
        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.BASE, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        builders[0] = new DefinedColumnBuilder(builders[0]);
        builders[1] = new DefinedColumnBuilder(builders[1]);
        builders[2] = new DefinedColumnBuilder(builders[2]);
        builders[3] = new DefinedColumnBuilder(builders[3]);
        builders[4] = new DefinedColumnBuilder(builders[4]);
        builders[5] = new DefinedColumnBuilder(builders[5]);
        ((DefinedColumnBuilder)builders[0]).setWeight(new Weight(2));
        ((DefinedColumnBuilder)builders[1]).setWeight(new Weight(5));
        ((DefinedColumnBuilder)builders[2]).setWeight(new Weight(2));
        ((DefinedColumnBuilder)builders[3]).setWeight(new Weight(3));
        ((DefinedColumnBuilder)builders[4]).setWeight(new Weight(2));
        ((DefinedColumnBuilder)builders[5]).setWeight(new Weight(1));
        ((DefinedColumnBuilder)builders[0]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder)builders[1]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder)builders[2]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder)builders[3]).setContribution(Contribution.POSITIVE);
        ((DefinedColumnBuilder)builders[4]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder)builders[5]).setContribution(Contribution.NEGATIVE);
        ((DefinedColumnBuilder)builders[0]).setNeed(NeedOfAnalisys.OBLIGATORY);
        ((DefinedColumnBuilder)builders[1]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder)builders[2]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder)builders[3]).setNeed(NeedOfAnalisys.OBLIGATORY);
        ((DefinedColumnBuilder)builders[4]).setNeed(NeedOfAnalisys.FACULTATIVE);
        ((DefinedColumnBuilder)builders[5]).setNeed(NeedOfAnalisys.OBLIGATORY);
        mb = new MatrixBuilder(MatrixBuilder.State.DEFINED, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        builders[0] = new DetailedColumnBuilder(builders[0]);
        builders[1] = new DetailedColumnBuilder(builders[1]);
        builders[2] = new DetailedColumnBuilder(builders[2]);
        builders[3] = new DetailedColumnBuilder(builders[3]);
        builders[4] = new DetailedColumnBuilder(builders[4]);
        builders[5] = new DetailedColumnBuilder(builders[5]);
        ((DetailedColumnBuilder)builders[0]).setScale(sc1);
        ((DetailedColumnBuilder)builders[1]).setScale(sc2);
        ((DetailedColumnBuilder)builders[2]).setScale(sc3);
        ((DetailedColumnBuilder)builders[3]).setScale(sc1);
        ((DetailedColumnBuilder)builders[4]).setScale(sc2);
        ((DetailedColumnBuilder)builders[5]).setScale(sc3);
        mb = new MatrixBuilder(MatrixBuilder.State.DETAILED, new MatrixVersion("1"));
        mb.addColumnBuilder(cvg1, builders[0]);
        mb.addColumnBuilder(cvg1, builders[1]);
        mb.addColumnBuilder(cvg2, builders[2]);
        mb.addColumnBuilder(cvg2, builders[3]);
        mb.addColumnBuilder(cvg3, builders[4]);
        mb.addColumnBuilder(cvg3, builders[5]);
        return mb.build(); 
    }
    
}
