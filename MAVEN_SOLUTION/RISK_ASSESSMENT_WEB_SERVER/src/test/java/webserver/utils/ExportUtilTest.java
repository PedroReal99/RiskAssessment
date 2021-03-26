/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Pedro
 */
public class ExportUtilTest {
    
    public ExportUtilTest() {
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
     * Test of transformIntoXML method, of class ExportUtil.
     */
    @Test
    public void testTransformIntoXML() {
        System.out.println("transformIntoXML");
        String body = "\"\\nName: Casa\"\n" +
"                + \"\\nPrimeira matriz apresenta 76\"\n" +
"                + \"\\nSegunda matriz apresenta 58\"\n" +
"                + \"\\nName: Telemovel\"\n" +
"                + \"\\nPrimeira matriz apresenta 76\"\n" +
"                + \"\\nSegunda matriz apresenta 58\"\n" +
"                + \"\\nMatriz 2 tem melhor índice de risco\"";
        ExportUtil instance = new ExportUtil();
        File expResult = new File("SE04-Export2.xml");
        File result = instance.transformIntoXML(body,"SE04-Export.xml" , "SE04" , "Result");
        assertEquals(expResult.getFreeSpace(), result.getFreeSpace());
    }

    /**
     * Test of validateXML method, of class ExportUtil.
     */
    @Test
    public void testValidateXML() {
        System.out.println("validateXML");
        File file = new File("SE04-ExportTest.xml");
        ExportUtil instance = new ExportUtil();
        boolean expResult = true;
        boolean result = instance.validateXML(file,"SchemaSE04Export.xsd");
        assertEquals(expResult, result);
    }



    /**
     * Test of transformXMLToJSON method, of class ExportUtil.
     */
    @Test
    public void testTransformXMLToJSON() throws Exception {
        System.out.println("transformXMLToJSON");
        File file = new File("SE04-Export2.xml");
        ExportUtil instance = new ExportUtil();
        File result = instance.transformXMLToJSON(file,"SE04-Export.json");
        File expResult = new File("SE04-Export2.json");
        assertEquals(expResult.getFreeSpace(),result.getFreeSpace());
    }


    /**
     * Test of transformXMLToXHTML method, of class ExportUtil.
     */
    @Test
    public void testTransformXMLToXHTML() {
        System.out.println("transformXMLToXHTML");
        File file = new File("SE04-Export.xml");
        ExportUtil instance = new ExportUtil();
        File expResult = new File("SE04XHTML2.xhtml");
        File result = instance.transformXMLToXHTML(file, "SE04XML2XHTML.xsl", "SE04XHTML.xhtml");
        assertEquals(expResult.getFreeSpace(), result.getFreeSpace());
    }


    
}
