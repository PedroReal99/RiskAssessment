/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.Insurance;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.ClassificationTable;
import core.domain.Insurance.ClassificationTables.TableLine;
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
public class InsuranceObjectTest {
    
//    List<String> detail = new ArrayList<>();
//    CaseCode ccc1 = new CaseCode("Caso 1");
//    CaseType ct1 = new CaseType("Casa");
//    CaseState cs1 = new CaseState("Á espera");
//    List<InsuranceObject> insurances = new ArrayList<>();
//    CaseI case1 = new CaseI(ct1, cs1, ccc1,insurances,false);
//    RiskFactor RSK1 = new RiskFactor(new Metric("Distancia"), new STName("Bombeiros"));
//    RiskFactor RSK2 = new RiskFactor(new Metric("Quantidade"), new STName("IE Saúde"));
//    RiskFactor RSK3 = new RiskFactor(new Metric("Densidade"), new STName("Zona Urbana"));
//    RiskFactor RSK4 = new RiskFactor(new Metric("Distancia"), new STName("Policia"));
//    RiskFactor RSK5 = new RiskFactor(new Metric("Quantidade"), new STName("Médicos"));
//    RiskFactor RSK6 = new RiskFactor(new Metric("Quantidade"), new STName("Policias"));
//    Classification c1 = new Classification("HIGH");
//    Classification c2 = new Classification("LOW");
//    Classification c3 = new Classification("MEDIUM");
//    Classification c4 = new Classification("(não detetado)");
//    Classification c5 = new Classification("HIGH");
//    Classification c6 = new Classification("(não detetado)");
//    ClassificationColumn cc1 = new ClassificationColumn(RSK1,c1);
//    ClassificationColumn cc2 = new ClassificationColumn(RSK2,c2);
//    ClassificationColumn cc3 = new ClassificationColumn(RSK3,c3);
//    ClassificationColumn cc4 = new ClassificationColumn(RSK4,c4);
//    ClassificationColumn cc5 = new ClassificationColumn(RSK5,c5);
//    ClassificationColumn cc6 = new ClassificationColumn(RSK6,c6);
//    Map<RiskFactor,ClassificationColumn> map1 = new HashMap<>();  
//    Map<RiskFactor,ClassificationColumn> map2 = new HashMap<>();
//    Map<RiskFactor,ClassificationColumn> map3 = new HashMap<>();
//    Coverage cvg1 = new Coverage(new CoverageName("Incendios"));
//    Coverage cvg2 = new Coverage(new CoverageName("Tempestades"));
//    Coverage cvg3 = new Coverage(new CoverageName("Tsunamis"));
//    TableLine tb1 = new TableLine(map1, cvg1);
//    TableLine tb2 = new TableLine(map2, cvg2);
//    TableLine tb3 = new TableLine(map3, cvg3);
//    Scale sc1 = new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5));
//    Scale sc2 = new Scale(new RiskLevel(2), new RiskLevel(4), new RiskLevel(6));
//    Scale sc3 = new Scale(new RiskLevel(3), new RiskLevel(6), new RiskLevel(9));
//    List<TableLine> list = new ArrayList<>();
//    List<TableLine> list2 = new ArrayList<>();
//    ClassificationTable t = new ClassificationTable(list);
//    InsuranceObject io = new InsuranceObject(new InsuranceName("Casa"), new PostLocation(),new CalculationDetail(detail), new RiskIndex(-1), t);
//    InsuranceObject io2 = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation(),new CalculationDetail(detail), new RiskIndex(-1), t);
//    
    
    public InsuranceObjectTest() {
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

//    /**
//     * Test of toString method, of class InsuranceObject.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        String expResult = "Name: Casa";
//        String result = io.toString();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of obtainDetails method, of class InsuranceObject.
//     */
//    @Test
//    public void testObtainDetails() {
//        System.out.println("obtainDetails");
//        String result = io.obtainDetails();
//        String expResult = "Calculation Details: " + new ArrayList<>().toString();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of obtainRiskIndex method, of class InsuranceObject.
//     */
//    @Test
//    public void testObtainRiskIndex() {
//        System.out.println("obtainRiskIndex");
//        String expResult = "riskIndex= -1";
//        String result = io.obtainRiskIndex();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of obtainLocation method, of class InsuranceObject.
//     */
//    @Test
//    public void testObtainLocation() {
//        System.out.println("obtainLocation");
//        String result = io.obtainLocation();
//        assertEquals(new PostLocation().toString(), result);
//    }
//
//    /**
//     * Test of obtainName method, of class InsuranceObject.
//     */
//    @Test
//    public void testObtainName() {
//        System.out.println("obtainName");
//        String expResult = "Name: Casa";
//        String result = io.obtainName();
//        assertEquals(expResult, result);
//    }
//
//    public int hashCodeCalculator() {
//        int hash = 7;
//        hash = 71 * hash + Objects.hashCode(io.obtainName());
//        return hash;
//    }
//    
//    //@Test
//    public void testHashCode() {
//        assertEquals(io.hashCode(), hashCodeCalculator());
//    }
//
//    /**
//     * Test of sameAs method, of class InsuranceObject.
//     */
//    @Test
//    public void testSameAs() {
//        System.out.println("sameAs");
//        Object obj = io;
//        assertTrue(io.sameAs(io));
//        assertFalse(io.sameAs(null));
//        assertTrue(io.sameAs(obj));
//        obj = new InsuranceObject();
//        assertFalse(io.sameAs(obj));
//        obj = new Object();
//        assertFalse(io.sameAs(obj));
//    }
//
//    /**
//     * Test of identity method, of class InsuranceObject.
//     */
//    @Test
//    public void testIdentity() {
//        System.out.println("identity");
//        InsuranceName expResult = new InsuranceName("Casa");
//        InsuranceName result = io.identity();
//        assertEquals(expResult, result);
//    }
//    
//    @Test(expected = IllegalArgumentException.class)
//    public void ensureAddFinalValueOfCalculationIsWorkingAsExpectedNegativeValue() {
//        System.out.println("addFinalValueOfCalculationIsWorkingAsExpected");
//        io.addFinalValueOfCalculation(-1);
//    }
//    
//    @Test
//    public void ensureAddFinalValueOfCalculationIsWorkingAsExpectedPositiveValue() {
//        System.out.println("addFinalValueOfCalculationIsWorkingAsExpected");
//        io.addFinalValueOfCalculation(0);
//        assertEquals(io.obtainRiskIndex() , new RiskIndex(0).toString());
//    }
//
//    /**
//     * Test of calculateFinalScore method, of class InsuranceObject.
//     */
//    @Test
//    public void testCalculateFinalScore() {
//        System.out.println("calculateFinalScore");
//        RiskMatrix rm = createMatrixTest();
//        boolean det = false;
//        CaseI c = createCaseTest();
//        for(InsuranceObject io1 :  c.getAssociatedInsuranceObjects()) {
//            io1.calculateFinalScore(rm, det);
//            assertEquals(io1.obtainRiskIndex() , new RiskIndex(60).toString());
//        }
//       
//    }
//    
//    public CaseI createCaseTest() {
//        Map<RiskFactor,ClassificationColumn> map1test = new HashMap<>();  
//        Map<RiskFactor,ClassificationColumn> map2test = new HashMap<>();
//        Map<RiskFactor,ClassificationColumn> map3test = new HashMap<>();
//        map1test.put(RSK1, cc1);
//        map1test.put(RSK2, cc2);
//        map2test.put(RSK3, cc3);
//        map2test.put(RSK4, cc4);
//        map3test.put(RSK5, cc5);
//        map3test.put(RSK6, cc6);
//        TableLine tb1test = new TableLine(map1test, cvg1);
//        TableLine tb2test = new TableLine(map2test, cvg2);
//        TableLine tb3test = new TableLine(map3test, cvg3);
//        List<TableLine> lt = new ArrayList<>();
//        lt.add(tb1test);
//        lt.add(tb2test);
//        lt.add(tb3test);
//        ClassificationTable t1 = new ClassificationTable(lt);
//        List<InsuranceObject> li = new ArrayList<>();
//        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation(),new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
//        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation(),new CalculationDetail(detail), new RiskIndex(-1), t1);
//        li.add(ioteste);
//        li.add(io2teste);
//        CaseI c = new CaseI(ct1, cs1, ccc1, li,false);
//        return c;
//    }
//    
//    public RiskMatrix createMatrixTest() {
//        ColumnBuilder builders[] = {ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
//            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
//            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
//            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
//            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
//            ColumnBuilder.newColumnBuilder(ColumnBuilder.State.BASE),
//        };
//        builders[0].setRiskFactor(RSK1);
//        builders[1].setRiskFactor(RSK2);
//        builders[2].setRiskFactor(RSK3);
//        builders[3].setRiskFactor(RSK4);
//        builders[4].setRiskFactor(RSK5);
//        builders[5].setRiskFactor(RSK6);
//        cvg1.associateRisk(RSK1);
//        cvg1.associateRisk(RSK2);
//        cvg2.associateRisk(RSK3);
//        cvg2.associateRisk(RSK4);
//        cvg3.associateRisk(RSK5);
//        cvg3.associateRisk(RSK6);
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.BASE, new MatrixVersion("1"));
//        mb.addColumnBuilder(cvg1, builders[0]);
//        mb.addColumnBuilder(cvg1, builders[1]);
//        mb.addColumnBuilder(cvg2, builders[2]);
//        mb.addColumnBuilder(cvg2, builders[3]);
//        mb.addColumnBuilder(cvg3, builders[4]);
//        mb.addColumnBuilder(cvg3, builders[5]);
//        builders[0] = new DefinedColumnBuilder(builders[0]);
//        builders[1] = new DefinedColumnBuilder(builders[1]);
//        builders[2] = new DefinedColumnBuilder(builders[2]);
//        builders[3] = new DefinedColumnBuilder(builders[3]);
//        builders[4] = new DefinedColumnBuilder(builders[4]);
//        builders[5] = new DefinedColumnBuilder(builders[5]);
//        ((DefinedColumnBuilder)builders[0]).setWeight(new Weight(2));
//        ((DefinedColumnBuilder)builders[1]).setWeight(new Weight(5));
//        ((DefinedColumnBuilder)builders[2]).setWeight(new Weight(2));
//        ((DefinedColumnBuilder)builders[3]).setWeight(new Weight(3));
//        ((DefinedColumnBuilder)builders[4]).setWeight(new Weight(2));
//        ((DefinedColumnBuilder)builders[5]).setWeight(new Weight(1));
//        ((DefinedColumnBuilder)builders[0]).setContribution(Contribution.POSITIVE);
//        ((DefinedColumnBuilder)builders[1]).setContribution(Contribution.NEGATIVE);
//        ((DefinedColumnBuilder)builders[2]).setContribution(Contribution.POSITIVE);
//        ((DefinedColumnBuilder)builders[3]).setContribution(Contribution.POSITIVE);
//        ((DefinedColumnBuilder)builders[4]).setContribution(Contribution.NEGATIVE);
//        ((DefinedColumnBuilder)builders[5]).setContribution(Contribution.NEGATIVE);
//        ((DefinedColumnBuilder)builders[0]).setNeed(NeedOfAnalisys.OBLIGATORY);
//        ((DefinedColumnBuilder)builders[1]).setNeed(NeedOfAnalisys.FACULTATIVE);
//        ((DefinedColumnBuilder)builders[2]).setNeed(NeedOfAnalisys.FACULTATIVE);
//        ((DefinedColumnBuilder)builders[3]).setNeed(NeedOfAnalisys.OBLIGATORY);
//        ((DefinedColumnBuilder)builders[4]).setNeed(NeedOfAnalisys.FACULTATIVE);
//        ((DefinedColumnBuilder)builders[5]).setNeed(NeedOfAnalisys.OBLIGATORY);
//        mb = new MatrixBuilder(MatrixBuilder.State.DEFINED, new MatrixVersion("1"));
//        mb.addColumnBuilder(cvg1, builders[0]);
//        mb.addColumnBuilder(cvg1, builders[1]);
//        mb.addColumnBuilder(cvg2, builders[2]);
//        mb.addColumnBuilder(cvg2, builders[3]);
//        mb.addColumnBuilder(cvg3, builders[4]);
//        mb.addColumnBuilder(cvg3, builders[5]);
//        builders[0] = new DetailedColumnBuilder(builders[0]);
//        builders[1] = new DetailedColumnBuilder(builders[1]);
//        builders[2] = new DetailedColumnBuilder(builders[2]);
//        builders[3] = new DetailedColumnBuilder(builders[3]);
//        builders[4] = new DetailedColumnBuilder(builders[4]);
//        builders[5] = new DetailedColumnBuilder(builders[5]);
//        ((DetailedColumnBuilder)builders[0]).setScale(sc1);
//        ((DetailedColumnBuilder)builders[1]).setScale(sc2);
//        ((DetailedColumnBuilder)builders[2]).setScale(sc3);
//        ((DetailedColumnBuilder)builders[3]).setScale(sc1);
//        ((DetailedColumnBuilder)builders[4]).setScale(sc2);
//        ((DetailedColumnBuilder)builders[5]).setScale(sc3);
//        mb = new MatrixBuilder(MatrixBuilder.State.DETAILED, new MatrixVersion("1"));
//        mb.addColumnBuilder(cvg1, builders[0]);
//        mb.addColumnBuilder(cvg1, builders[1]);
//        mb.addColumnBuilder(cvg2, builders[2]);
//        mb.addColumnBuilder(cvg2, builders[3]);
//        mb.addColumnBuilder(cvg3, builders[4]);
//        mb.addColumnBuilder(cvg3, builders[5]);
//        return mb.build(); 
//    }
    
}
