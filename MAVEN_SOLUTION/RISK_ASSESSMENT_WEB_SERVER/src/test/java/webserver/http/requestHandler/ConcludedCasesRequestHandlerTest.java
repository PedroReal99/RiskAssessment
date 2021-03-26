/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CasePriority;
import core.domain.Case.CaseState;
import core.domain.Case.CaseType;
import core.domain.Coverage.Coverage;
import core.domain.Coverage.CoverageName;
import core.domain.Insurance.CalculationDetail;
import core.domain.Insurance.Classification;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.ClassificationTable;
import core.domain.Insurance.ClassificationTables.TableLine;
import core.domain.Insurance.InsuranceName;
import core.domain.Insurance.InsuranceObject;
import core.domain.Insurance.RiskIndex;
import core.domain.RiskFactors.Metric;
import core.domain.RiskFactors.RiskFactor;
import core.domain.RiskMatrixs.Attributes.RiskLevel;
import core.domain.RiskMatrixs.Attributes.Scale;
import core.domain.Surrounding.STName;
import core.domain.User.Email;
import core.domain.location.PostLocation;
import core.persistence.PersistenceContext;
import java.io.File;
import java.io.StringReader;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import webserver.controller.ConcludedCasesController;
import webserver.http.Config;
import webserver.http.HTTPConnector;
import webserver.http.HTTPmessage;
import webserver.threadController.ThreadController;
import webserver.threadController.ThreadTimeController;

/**
 *
 * @author joaoflores
 */
public class ConcludedCasesRequestHandlerTest {

    HTTPConnector h = new HTTPConnector(new Socket(), new ThreadController(1, 1), new ThreadTimeController(1));
    ConcludedCasesController cc = new ConcludedCasesController();

    static List<String> detail = new ArrayList<>();
    static CaseCode ccc1 = new CaseCode("Code");
    static CaseType ct1 = new CaseType("Aceite");
    static CaseState cs1 = new CaseState("Finalizada");
    static CasePriority cp1 = CasePriority.MAX;
    static List<InsuranceObject> insurances = new ArrayList<>();
    static CaseI case1 = new CaseI(ct1, cs1, ccc1, insurances, false, cp1, new CaseDate("06/06/06"));
    static RiskFactor RSK1 = new RiskFactor(Metric.DISTANCIA, new STName("Bombeiros"));
    static RiskFactor RSK2 = new RiskFactor(Metric.QUANTIDADE, new STName("IE Saúde"));
    static RiskFactor RSK3 = new RiskFactor(Metric.QUANTIDADE, new STName("Zona Urbana"));
    static RiskFactor RSK4 = new RiskFactor(Metric.DISTANCIA, new STName("Policia"));
    static RiskFactor RSK5 = new RiskFactor(Metric.QUANTIDADE, new STName("Médicos"));
    static RiskFactor RSK6 = new RiskFactor(Metric.QUANTIDADE, new STName("Policias"));
    static Classification c1 = new Classification("HIGH");
    static Classification c2 = new Classification("LOW");
    static Classification c3 = new Classification("MEDIUM");
    static Classification c4 = new Classification("(não detetado)");
    static Classification c5 = new Classification("HIGH");
    static Classification c6 = new Classification("(não detetado)");
    static ClassificationColumn cc1 = new ClassificationColumn(RSK1, c1);
    static ClassificationColumn cc2 = new ClassificationColumn(RSK2, c2);
    static ClassificationColumn cc3 = new ClassificationColumn(RSK3, c3);
    static ClassificationColumn cc4 = new ClassificationColumn(RSK4, c4);
    static ClassificationColumn cc5 = new ClassificationColumn(RSK5, c5);
    static ClassificationColumn cc6 = new ClassificationColumn(RSK6, c6);
    static ClassificationColumn cc7 = new ClassificationColumn(RSK6, c1);
    static Map<RiskFactor, ClassificationColumn> map1 = new HashMap<>();
    static Map<RiskFactor, ClassificationColumn> map2 = new HashMap<>();
    static Map<RiskFactor, ClassificationColumn> map3 = new HashMap<>();
    static Coverage cvg1 = new Coverage(new CoverageName("Incendios"));
    static Coverage cvg2 = new Coverage(new CoverageName("Tempestades"));
    static Coverage cvg3 = new Coverage(new CoverageName("Tsunamis"));
    static TableLine tb1 = new TableLine(map1, cvg1);
    static TableLine tb2 = new TableLine(map2, cvg2);
    static TableLine tb3 = new TableLine(map3, cvg3);
    static Scale sc1 = new Scale(new RiskLevel(1), new RiskLevel(3), new RiskLevel(5));
    static Scale sc2 = new Scale(new RiskLevel(2), new RiskLevel(4), new RiskLevel(6));
    static Scale sc3 = new Scale(new RiskLevel(3), new RiskLevel(6), new RiskLevel(9));
    static List<TableLine> list = new ArrayList<>();
    static List<TableLine> list2 = new ArrayList<>();
    static ClassificationTable t = new ClassificationTable(list);
    static ClassificationTable t2 = new ClassificationTable(list);
    static InsuranceObject io = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t);
    static InsuranceObject io2 = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t2); //List não está a ser preenchida

    static CaseI instance1 = createCaseTest1();
    static CaseI instance2 = createCaseTest2();
    static CaseI instance3 = createCaseTest3();
    static CaseI instance4 = createCaseTest4();
    static CaseI instance5 = createCaseTest5();
    static CaseI instance6 = createCaseTest6();

    public ConcludedCasesRequestHandlerTest() {
    }

     @BeforeClass
    public static void setUpClass() {
        PersistenceContext.repositories().caseRepository().save(instance1);
        PersistenceContext.repositories().caseRepository().save(instance2);
        PersistenceContext.repositories().caseRepository().save(instance3);
        PersistenceContext.repositories().caseRepository().save(instance4);
        PersistenceContext.repositories().caseRepository().save(instance5);
        PersistenceContext.repositories().caseRepository().save(instance6);
    }

    @AfterClass
    public static void tearDownClass() {
        //PersistenceContext.repositories().caseRepository().delete(instance1);
        //PersistenceContext.repositories().caseRepository().delete(instance2);
        //PersistenceContext.repositories().caseRepository().delete(instance3);
        //PersistenceContext.repositories().caseRepository().delete(instance4);
        //PersistenceContext.repositories().caseRepository().delete(instance5);
        //PersistenceContext.repositories().caseRepository().delete(instance6);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of handle method, of class ConcludedCasesRequestHandler.
     */
    //@Test
    public void testHandle() {
        System.out.println("handle");
        HTTPmessage request = new HTTPmessage();
        request.setURI("riskAssessment/cases/obtain?export=xml");
        request.setRequestMethod("POST");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        request.setContentFromFile("input.xml");
        ConcludedCasesRequestHandler instance = new ConcludedCasesRequestHandler();
        HTTPmessage expResult = new HTTPmessage();
        expResult.setResponseStatus(Config.POSITIVE_RESPONSE);
        expResult.setContentFromFile("input.xml");
        HTTPmessage result = instance.handle(request);
        assertEquals(expResult.getStatus(), result.getStatus());
    }

    /**
     * Test of parseCompletedCases method, of class
     * ConcludedCasesRequestHandler.
     * @throws java.lang.Exception
     */
    @Test
    public void testParseCompletedCases() throws Exception {
        System.out.println("parseCompletedCases");
        String content
                = "<requests>   "
                + "  <request>   "
                + "      <parametersFromRequest>   "
                + "      	<matrixVersion>1</matrixVersion>    "
                + "             <state>Processado</state>            "
                + "             <initialDate></initialDate>           "
                + "             <finalDate></finalDate>              "
                + "              <cities>             	"
                + "                     <city>Porto</city>  "
                + "              </cities>       "
                + "       </parametersFromRequest> "
                + "    </request> "
                + "</requests>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(content)));
        java.io.Writer writer = new java.io.FileWriter("input.xml");
        XMLSerializer xml = new XMLSerializer(writer, null);
        xml.serialize(doc);

        Element keysElement = doc.getDocumentElement();
        if (!keysElement.getTagName().equals("request")) {
            keysElement = (Element) keysElement.getElementsByTagName("request").item(0);
        }

        NodeList iObjects = keysElement.getElementsByTagName("parametersFromRequest");
        String matrixVersion = "1";
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance4);
        expResult.add(instance5);
        expResult.add(instance6);
        List<CaseI> result = ConcludedCasesRequestHandler.parseCompletedCases(iObjects, matrixVersion);
        assertEquals(expResult, result);
    }

    /**
     * Test of casesSatisfingRequest method, of class
     * ConcludedCasesRequestHandler.
     */
    @Test
    public void testCasesSatisfingRequest() throws Exception {
        System.out.println("casesSatisfingRequest");
        CaseState stateOfCase = CaseState.PROCESSED;
        CaseDate startDate = new CaseDate("");
        CaseDate finalDate = new CaseDate("");
        List<String> cities = new ArrayList<>();
        cities.add("Porto");
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance4);
        expResult.add(instance5);
        expResult.add(instance6);
        List<CaseI> result = ConcludedCasesRequestHandler.casesSatisfingRequest(stateOfCase, startDate, finalDate, cities);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCaseResultsFilteredByCities method, of class
     * ConcludedCasesRequestHandler.
     */
    @Test
    public void testGetCaseResultsFilteredByCities() {
        System.out.println("getCaseResultsFilteredByCities");
        List<String> cities = new ArrayList<>();
        cities.add("Porto");
        List<CaseI> allCasesByState = new ArrayList<>();
        allCasesByState.add(instance4);
        allCasesByState.add(instance5);
        allCasesByState.add(instance6);
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance4);
        expResult.add(instance5);
        expResult.add(instance6);
        List<CaseI> result = ConcludedCasesRequestHandler.getCaseResultsFilteredByCities(cities, allCasesByState);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCaseResultsFilteredByCities method, of class
     * ConcludedCasesRequestHandler.
     */
    @Test
    public void testGetCaseResultsFilteredByDates() throws ParseException {
        System.out.println("getCaseResultsFilteredByCities");
        List<String> cities = new ArrayList<>();
        cities.add("Porto");
        List<CaseI> allCasesByState = new ArrayList<>();
        allCasesByState.add(instance4);
        allCasesByState.add(instance5);
        allCasesByState.add(instance6);
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance4);
        expResult.add(instance5);
        expResult.add(instance6);
        List<CaseI> result = ConcludedCasesRequestHandler.getCaseResultsFilteredByTimeInterval(new CaseDate("06/06/2006"), new CaseDate("23/12/2020"), allCasesByState);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCaseResultsFilteredByCities method, of class
     * ConcludedCasesRequestHandler.
     */
    @Test
    public void testGetCaseResultsFilteredByDatesAndCities() throws ParseException {
        System.out.println("getCaseResultsFilteredByCitiesandDates");
        List<String> cities = new ArrayList<>();
        cities.add("Porto");
        List<CaseI> allCasesByState = new ArrayList<>();
        allCasesByState.add(instance4);
        allCasesByState.add(instance5);
        allCasesByState.add(instance6);
        List<CaseI> expResult = new ArrayList<>();
        expResult.add(instance4);
        expResult.add(instance5);
        expResult.add(instance6);
        List<CaseI> result = ConcludedCasesRequestHandler.getCaseResultsFilteredByTimeIntervalAndCities(new CaseDate("06/06/2006"), new CaseDate("23/12/2020"), cities, allCasesByState);
        assertEquals(expResult, result);
    }

    /**
     * Test of buildResponse method, of class ConcludedCasesRequestHandler.
     */
    @Test
    public void testBuildResponse() {
        System.out.println("buildResponse");
        File file = new File("SE03.xml");
        HTTPmessage expResponse = new HTTPmessage();
        expResponse.setContentFromFile(file.getName());
        expResponse.setResponseStatus(Config.POSITIVE_RESPONSE);
        HTTPmessage response = new HTTPmessage();
        ConcludedCasesRequestHandler instance = new ConcludedCasesRequestHandler();
        instance.buildResponse(response, file, Config.POSITIVE_RESPONSE);
        assertArrayEquals(expResponse.getContent(), response.getContent());
        assertEquals(expResponse.getStatus(), response.getStatus());
    }

    /**
     * Test of exportIntoDesiredFileType method, of class
     * ConcludedCasesRequestHandler.
     */
    @Test
    public void testExportIntoDesiredFileType() throws Exception {
        System.out.println("exportIntoDesiredFileType");
        String filetype = "XML";
        String content = "Case Information:\n"
                + "Identifier:Code4\n"
                + "State:State: Processado\n"
                + "Result\n"
                + "Carro-&gt; 76\n"
                + "Mota-&gt; 76\n"
                + "\n"
                + "Case Information:\n"
                + "Identifier:Code33\n"
                + "State:State: Processado\n"
                + "Result\n"
                + "Habitacao-&gt; 76\n"
                + "Roubos-&gt; 76\n"
                + "\n"
                + "Case Information:\n"
                + "Identifier:Code12\n"
                + "State:State: Processado\n"
                + "Result\n"
                + "Habitacao-&gt; 76\n"
                + "Roubos-&gt; 76";
        ConcludedCasesRequestHandler instance = new ConcludedCasesRequestHandler();
        instance.exportIntoDesiredFileType(filetype, content);
        assertEquals(new File("SE03_Test.xml").getFreeSpace(), new File("SE03.xml").getFreeSpace());
        filetype = "XHTML";
        instance.exportIntoDesiredFileType(filetype, content);
        assertEquals(new File("SE03_Test.xhtml").getFreeSpace(), new File("SE03.xhtml").getFreeSpace());
        filetype = "JSON";
        instance.exportIntoDesiredFileType(filetype, content);
        assertEquals(new File("SE03_Test.json").getFreeSpace(), new File("SE03.json").getFreeSpace());
        File f = instance.exportIntoDesiredFileType("TXT", content);
        assertNull(f);
    }

    /**
     * Aqui se econtram a criação dos casos para tudo o que seja dados experados
     */
    public static CaseI createCaseTest1() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
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
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, cs1, ccc1, li, false, cp1, new CaseDate("06/06/2016"), new Email("admin1@gmail.com"));
        return c;
    }

    public static CaseI createCaseTest2() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
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
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.WAITING, new CaseCode("Code1"), li, false, cp1, new CaseDate("06/06/2016"));
        return c;
    }

    public static CaseI createCaseTest3() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
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
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Casa"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Telemóvel"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.WAITING, new CaseCode("Code3"), li, false, cp1, new CaseDate("06/06/2018"));
        return c;
    }

    public static CaseI createCaseTest4() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
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
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Carro"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Mota"), new PostLocation("Portugal", "Aveiro", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.PROCESSED, new CaseCode("Code4"), li, false, cp1, new CaseDate("06/06/2018"));
        c.alterDate("06/06/2020");
        return c;
    }

    public static CaseI createCaseTest5() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
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
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Habitacao"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Roubos"), new PostLocation("Portugal", "Aveiro", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.PROCESSED, new CaseCode("Code33"), li, false, cp1, new CaseDate("06/06/2019"));
        c.alterDate("23/12/2020");
        return c;
    }

    public static CaseI createCaseTest6() {
        Map<RiskFactor, ClassificationColumn> map1test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map2test = new HashMap<>();
        Map<RiskFactor, ClassificationColumn> map3test = new HashMap<>();
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
        InsuranceObject ioteste = new InsuranceObject(new InsuranceName("Habitacao"), new PostLocation("Portugal", "Porto", "123ed", "1", "4480"), new CalculationDetail(new ArrayList<>()), new RiskIndex(-1), t1);
        InsuranceObject io2teste = new InsuranceObject(new InsuranceName("Roubos"), new PostLocation("Portugal", "Aveiro", "123ed", "1", "4480"), new CalculationDetail(detail), new RiskIndex(-1), t1);
        li.add(ioteste);
        li.add(io2teste);
        CaseI c = new CaseI(ct1, CaseState.PROCESSED, new CaseCode("Code12"), li, false, cp1, new CaseDate("06/06/2019"));
        c.alterDate("27/06/2030");
        return c;
    }

}
