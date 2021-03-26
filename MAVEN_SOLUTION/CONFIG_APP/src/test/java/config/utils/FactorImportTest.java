// /*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package config.utils;
//
//import core.domain.RiskFactors.Metric;
//import core.domain.RiskFactors.RiskFactor;
//import core.domain.RiskFactors.STName;
//import core.domain.RiskFactors.SurroundingType;
//import core.persistence.CoverageRepository;
//import core.persistence.PersistenceContext;
//import core.persistence.RiskFactorRepository;
//import core.persistence.SurroundingTypeRepository;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import config.Utils.FactorImport;
//import core.domain.Coverage.Coverage;
//import core.domain.Coverage.CoverageName;
//import core.domain.RiskMatrixs.Attributes.Contribution;
//import core.domain.RiskMatrixs.Attributes.NeedOfAnalisys;
//import core.domain.RiskMatrixs.Attributes.Weight;
//import core.domain.RiskMatrixs.Columns.ColumnBuilder;
//import core.domain.RiskMatrixs.Columns.DefinedColumnBuilder;
//import core.domain.RiskMatrixs.MatrixBuilder;
//
///**
// *
// * @author morei
// */
//public class FactorImportTest {
//
//    SurroundingType s1 = new SurroundingType(new STName("Bombeiros"));
//    Metric m1 = new Metric("Distancia");
//    RiskFactor rf1 = new RiskFactor(m1, s1.obtainSTName());
//
//    Metric m2 = new Metric("Tempo");
//    RiskFactor rf2 = new RiskFactor(m2, s1.obtainSTName());
//
//    SurroundingType s2 = new SurroundingType(new STName("Floresta"));
//    Metric m3 = new Metric("Area");
//    RiskFactor rf3 = new RiskFactor(m3, s2.obtainSTName());
//
//    SurroundingType s3 = new SurroundingType(new STName("Vias Acesso"));
//    Metric m4 = new Metric("Quantidade");
//    RiskFactor rf4 = new RiskFactor(m4, s3.obtainSTName());
//
//    Coverage c1 = new Coverage(new CoverageName(("Incendio")));
//
//    CoverageRepository coverageRepository = PersistenceContext.repositories().coverageRepository();
//    RiskFactorRepository riskFactorRepository = PersistenceContext.repositories().riskRepository();
//    SurroundingTypeRepository surroundingTypeRepository = PersistenceContext.repositories().surroundingRepository();
//
//    public FactorImportTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        surroundingTypeRepository.save(s1);
//        surroundingTypeRepository.save(s2);
//        surroundingTypeRepository.save(s3);
//
//        coverageRepository.save(c1);
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void testListRiskFactor() throws IOException {
//        System.out.println("listRiskFactor");
//        String[] f1 = {"", "Bombeiros", "", "Floresta", "Vias Acesso"};
//        String[] f2 = {"", "Distancia", "Tempo", "Area", "Quantidade"};
//        FactorImport instance = new FactorImport();
// 
//        List<RiskFactor> expResult = new ArrayList<>();
//        expResult.add(rf1);
//        expResult.add(rf2);
//        expResult.add(rf3);
//        expResult.add(rf4)
// 
//        List<RiskFactor> result = instance.getListRiskFactor(f1, f2);
//        assertArrayEquals(expResult.toArray(), result.toArray());
//    }
 

 
//    /**
//     * Test of addToMatrix method, of class FactorImport.
//     */
//    @Test
//    public void testAddToMatrixPeso() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "2", "3", "", "2"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Peso", rf1, line, cb, c1, 1);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setWeight(new Weight(Integer.parseInt(line[1])));
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
// 
//    @Test
//    public void testAddToMatrixNecedidadeFacultativo() {
//
//        List<RiskFactor> result = instance.getListRiskFactor(f1, f2);
//        assertArrayEquals(expResult.toArray(), result.toArray());
//    }
//
//    /**
//     * Test of importFactorValues method, of class FactorImport.
//     */
////    @Test
////    public void testImportFactorValues() throws Exception {
////        System.out.println("importFactorValues");
////        String fileName = "";
////        MatrixBuilder mb = null;
////        FactorImport instance = new FactorImport();
////        instance.importFactorValues(fileName, mb);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//
//    /**
//     * Test of addToMatrix method, of class FactorImport.
//     */
//    @Test
//    public void testAddToMatrixPeso() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "2", "3", "", "2"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Peso", rf1, line, cb, c1, 1);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setWeight(new Weight(Integer.parseInt(line[1])));
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
//
//    @Test
//    public void testAddToMatrixNecedidadeFacultativo() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "Facultativo", "Facultativo", "", "Obrigatório"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Necessidade", rf1, line, cb, c1, 1);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setNeed(NeedOfAnalisys.FACULTATIVE);
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
//
//    @Test
//    public void testAddToMatrixNecedidadeObrigatorio() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "Facultativo", "Facultativo", "", "Obrigatório"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Necessidade", rf1, line, cb, c1, 1);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setNeed(NeedOfAnalisys.FACULTATIVE);
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
// 
//    @Test
//    public void testAddToMatrixNecedidadeObrigatorio() {
//        instance.addToMatrix(mb, "Necessidade", rf1, line, cb, c1, 4);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setNeed(NeedOfAnalisys.OBLIGATORY);
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
//
//    @Test
//    public void testAddToMatrixImportantesSim() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "Facultativo", "Facultativo", "", "Obrigatório"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Necessidade", rf1, line, cb, c1, 4);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setNeed(NeedOfAnalisys.OBLIGATORY);
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
// 
//    @Test
//    public void testAddToMatrixImportantesSim() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "Facultativo", "Facultativo", "", "Obrigatório"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Necessidade", rf1, line, cb, c1, 1);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setContribution(Contribution.POSITIVE);
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
// 
//
//    @Test
//    public void testAddToMatrixImportantesNao() {
//        System.out.println("addToMatrix");
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        String[] line = {"Tempestades", "Sim", "Nao", "", "Sim"};
//        DefinedColumnBuilder cb = null;
//        FactorImport instance = new FactorImport();
//        instance.addToMatrix(mb, "Importante", rf1, line, cb, c1, 2);
//        MatrixBuilder expected = new MatrixBuilder(MatrixBuilder.State.DEFINED);
//        cb = (DefinedColumnBuilder) ColumnBuilder.newColumnBuilder(ColumnBuilder.State.DEFINED);
//        cb.setContribution(Contribution.NEGATIVE);
//        cb.setRiskFactor(rf1);
//        expected.addColumnBuilder(c1, (ColumnBuilder) cb);
//    }
//    
//    
//    
//    /**
//     * Test of importMeaningfulFactors method, of class FactorImport.
//     */
//    @Test
//    public void testImportMeaningfulFactors() throws Exception {
//        System.out.println("importMeaningfulFactors");
//        String fileName = "MeaningfullFactorsImport.csv";
//        MatrixBuilder mb = new MatrixBuilder(MatrixBuilder.State.BASE);
//        FactorImport instance = new FactorImport();
//        boolean expResult = true;
//        String a = mb.toString();
//        System.out.println(a);
//        boolean result = instance.importMeaningfulFactors(fileName, mb);
//        assertEquals(expResult, result);
//    }
// 
// 
// 
//    /**
//     * Test of imporBaseRiskMatrix method, of class FactorImport.
//     */
//    @Test
//    public void testImporBaseRiskMatrix() throws Exception {
//        System.out.println("imporBaseRiskMatrix");
//        String fileName = "BaseImport.csv";
//        MatrixBuilder matrixBuilder = new MatrixBuilder(MatrixBuilder.State.BASE);
//        FactorImport instance = new FactorImport();
//        boolean expResult = true;
//        boolean result = instance.importBaseRiskMatrix(fileName, matrixBuilder);
//        assertEquals(expResult, result);
//    }
 
    /**
     * Test of imporDetailedRiskMatrix method, of class FactorImport.
     */
//    @Test
//    public void testImporDetailedRiskMatrix() throws Exception {
//        System.out.println("imporDetailedRiskMatrix");
//        String fileName = "";
//        MatrixBuilder matrixBuilder = null;
//        FactorImport instance = new FactorImport();
//        boolean expResult = false;
//        boolean result = instance.imporDetailedRiskMatrix(fileName, matrixBuilder);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//}
//        boolean result = instance.importMeaningfulFactors(fileName, mb);
//        assertEquals(expResult, result);
//    }
//    /**
//     * Test of imporBaseRiskMatrix method, of class FactorImport.
//     */
////    @Test
////    public void testImporBaseRiskMatrix() throws Exception {
////        System.out.println("imporBaseRiskMatrix");
////        String fileName = "";
////        MatrixBuilder matrixBuilder = null;
////        FactorImport instance = new FactorImport();
////        boolean expResult = false;
////        boolean result = instance.imporBaseRiskMatrix(fileName, matrixBuilder);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//
//    /**
//     * Test of imporDetailedRiskMatrix method, of class FactorImport.
//     */
////    @Test
////    public void testImporDetailedRiskMatrix() throws Exception {
////        System.out.println("imporDetailedRiskMatrix");
////        String fileName = "";
////        MatrixBuilder matrixBuilder = null;
////        FactorImport instance = new FactorImport();
////        boolean expResult = false;
////        boolean result = instance.imporDetailedRiskMatrix(fileName, matrixBuilder);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//}
