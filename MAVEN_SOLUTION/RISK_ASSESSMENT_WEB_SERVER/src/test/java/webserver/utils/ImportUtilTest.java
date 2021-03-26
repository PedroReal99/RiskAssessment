/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import webserver.utils.ImportUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Pedro
 */
public class ImportUtilTest {

    public ImportUtilTest() {
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
     * Test of transformIntoXML method, of class ImportUtil.
     */
    @Test
    public void testTransformIntoXML() {
        System.out.println("transformIntoXML");
        String body = "<SE04>\n" +
                "    <CaseCode> Code </CaseCode>\n" +
                "    <Versions>\n" +
                "        <Version>1</Version>\n" +
                "        <Version>2</Version>\n" +
                "    </Versions>\n" +
                "</SE04>";
        ImportUtil instance = new ImportUtil();
        File expResult = new File("a.xml");
//        File result = instance.transformIntoXML(body, "S04.xml");
//        assertEquals(expResult.getFreeSpace(), result.getFreeSpace());
    }

    /**
     * Test of validateXML method, of class ImportUtil.
     */
    @Test
    public void testValidateXML() {
        System.out.println("validateXML");
        File file = new File("a.xml");
        ImportUtil instance = new ImportUtil();
        //    boolean result =instance.validateXML(file, "SchemaSE04.xsd","se04");
        //  boolean expResult = true;
        // assertEquals(expResult, result);
    }

    /**
     * Test of getInformationFromXML method, of class ImportUtil.
     */
    @Test
    public void testGetInformationFromXML() {
        System.out.println("getInformationFromXML");
        File file = new File("a.xml");
        ImportUtil instance = new ImportUtil();
        List<String> expResult = new ArrayList<>();
        expResult.add(" Code ");
        expResult.add("1");
        expResult.add("2");
        List<String> result = instance.getInformationFromXML(file);
        assertEquals(expResult.get(0), result.get(0));
        assertEquals(expResult.get(1), result.get(1));
        assertEquals(expResult.get(2), result.get(2));
    }

    /**
     * Test of transformIntoJson method, of class ImportUtil.
     */
    //@Test
    public void testTransformIntoJson() throws Exception {
        System.out.println("transformIntoJson");
        String body = "<SE04>\n" +
                "    <CaseCode> Code </CaseCode>\n" +
                "    <Versions>\n" +
                "        <Version>1</Version>\n" +
                "        <Version>2</Version>\n" +
                "    </Versions>\n" +
                "</SE04>";
        ImportUtil instance = new ImportUtil();
        File expResult = new File("a.xml");
        File result = instance.transformIntoJson(body);
        assertEquals(expResult.getFreeSpace(), result.getFreeSpace());
    }

    /**
     * Test of getInformationFromJSON method, of class ImportUtil.
     */
    @Test
    public void testGetInformationFromJSON() throws Exception {
        System.out.println("getInformationFromJSON");
        File file = new File("teste.json");
        ImportUtil instance = new ImportUtil();
        List<String> expResult = new ArrayList<>();
        expResult.add("Code");
        expResult.add("1");
        expResult.add("2");
        List<String> result = instance.getInformationFromJSON(file);
        assertEquals(expResult, result);
    }

}
