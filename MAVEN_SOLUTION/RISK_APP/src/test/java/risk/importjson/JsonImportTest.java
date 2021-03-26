///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package risk.importjson;
//
//import core.domain.Case.CaseI;
//import core.domain.Case.CaseCode;
//import core.domain.Case.CaseState;
//import core.domain.Case.CaseType;
//import core.domain.Coverage.Coverage;
//import core.domain.Coverage.CoverageName;
//import core.domain.Insurance.CalculationDetail;
//import core.domain.Insurance.InsuranceName;
//import core.domain.Insurance.InsuranceObject;
//import core.domain.Insurance.GPSLocation;
//import core.domain.Insurance.RiskIndex;
//import core.domain.RiskFactors.Metric;
//import core.domain.RiskFactors.RiskFactor;
//import core.domain.RiskFactors.STName;
//import core.domain.RiskFactors.SurroundingType;
//import core.persistence.CaseRepository;
//import core.persistence.CoverageRepository;
//import core.persistence.PersistenceContext;
//import core.persistence.RiskFactorRepository;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import org.json.simple.parser.ParseException;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Ignore;
//
///**
// *
// * @author Pedro
// */
//public class JsonImportTest {
//
//    CaseCode cc1 = new CaseCode("Code");
//    CaseType ct1 = new CaseType("Aceite");
//    CaseState cs1 = new CaseState("Finalizada");
//    CaseI case1 = new CaseI(ct1, cs1, cc1);
//
//    CaseCode cc2 = new CaseCode("198765");
//    CaseType ct2 = new CaseType("Rip");
//    CaseState cs2 = new CaseState("Finqswefgr");
//    CaseI case2 = new CaseI(ct2, cs2, cc2);
//
//    CoverageName stn1 = new CoverageName("incendio");
//    Coverage coverage = new Coverage(stn1);
//
//    Metric metric = new Metric("metrica");
//    SurroundingType sou = new SurroundingType(new STName("floresta"));
//    RiskFactor risk = new RiskFactor(metric, new STName("floresta"));
//
//    CaseRepository caserepository;
//    RiskFactorRepository rfr;
//    CoverageRepository cR;
//
//    public JsonImportTest() {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("corePersistenceUnit");
//        EntityManager manager = factory.createEntityManager();
//        this.caserepository = PersistenceContext.repositories().caseRepository();
//        this.cR = PersistenceContext.repositories().coverageRepository();
//        this.rfr = PersistenceContext.repositories().riskRepository();
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//
//        caserepository.save(case1);
//        cR.save(coverage);
//        rfr.save(risk);
//
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of JsonFileReader method, of class JsonImport.
//     */
//    @Test
//    public void testJsonFileReader() throws Exception {
//        System.out.println("JsonFileReader");
//        String nomeFich = "completo.json";
//        JsonImport instance = new JsonImport();
//        List<String> expResult = new ArrayList<>();
//        expResult.add("Code1");
//        expResult.add("Aceite");
//        expResult.add("Finalizada");
//        expResult.add("PC");
//        expResult.add("20-50");
//        expResult.add("sem detalhes");
//        expResult.add("2");
//        expResult.add("incendio");
//        expResult.add("floresta");
//        expResult.add("metrica");
//        expResult.add("boa classificacao");
//
//        List<String> result = instance.JsonFileReader(nomeFich);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of createCase method, of class JsonImport.
//     */
//    //devido a ter de adicionar a base de dados
////    @Test
////    public void testCreateCase() throws Exception {
////        System.out.println("createCase");
////        String nomeFich = "completo.json";
////        JsonImport instance = new JsonImport();
////        CaseI expResult = new CaseI(new CaseType("Aceite"), new CaseState("Finalizada"), new CaseCode("Code1"));
////        GPSLocation l1 = new GPSLocation(20, 50);
////        List<String> lista = new ArrayList<>();
////        lista.add("sem detalhes");
////        CalculationDetail c = new CalculationDetail(lista);
////        InsuranceName name1 = new InsuranceName("PC");
////        RiskIndex r1 = new RiskIndex(2);
////        InsuranceObject ob1 = new InsuranceObject(name1, l1, c, r1, null);
////        expResult.associateInsurance(ob1);
////        CaseI result = instance.createCase(nomeFich);
////
////         assertEquals(expResult.obtainCode(), result.obtainCode());
////        assertEquals(expResult.obtainState(), result.obtainState());
////        assertEquals(expResult.obtainType(), result.obtainType());
////        
////        
////    }
//
//    /**
//     * Test of createCase method, of class JsonImport.
//     */
//    @Test
//    public void testGetExistingCases() throws IOException, ParseException {
//        System.out.println("getExistingCases");
//        String nomeFich = "json.json";
//        JsonImport instance = new JsonImport();
//        CaseI result = instance.getExistingCases(nomeFich);
//        assertEquals(case1, result);
//    }
//
//}
