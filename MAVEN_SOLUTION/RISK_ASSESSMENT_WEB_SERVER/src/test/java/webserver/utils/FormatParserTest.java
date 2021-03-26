/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class FormatParserTest {

    public FormatParserTest() {
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
     * Test of getTextFormat method, of class FormatParser.
     */
    @Test
    public void testGetTextFormat() {
        System.out.println("getTextFormat");
        String format = "";
        TextFormat expResult = TextFormat.NOTYPE;
        TextFormat result = FormatParser.getTextFormat(format);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTextFormat method, of class FormatParser.
     */
    @Test
    public void testGetTextFormaXMLt() {
        System.out.println("getTextFormat");
        String format = "application/xml";
        TextFormat expResult = TextFormat.XML;
        TextFormat result = FormatParser.getTextFormat(format);
        assertEquals(expResult, result);
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test
    public void testConvertJSONXML() {
        System.out.println("convert");
        TextFormat fromFormat = TextFormat.JSON;
        TextFormat toFormat = TextFormat.XML;
        String text = "{\n"
                + "  \"boolean\": true,\n"
                + "  \"array\": [\n"
                + "    1,\n"
                + "    2,\n"
                + "    3\n"
                + "  ],\n"
                + "  \"double\": 2,\n"
                + "  \"name\": \"JSON\",\n"
                + "  \"integer\": 1,\n"
                + "  \"nested\": {\"id\": 42}\n"
                + "}";
        String expResult = FormatParser.convert(toFormat, toFormat, "<boolean>true</boolean>\n"
                + "  <array>1</array>\n"
                + "  <array>2</array>\n"
                + "  <array>3</array>\n"
                + "  <double>2</double>\n"
                + "  <name>JSON</name>\n"
                + "  <integer>1</integer>\n"
                + "  <nested>\n"
                + "    <id>42</id>\n"
                + "  </nested>\n");

        String result = FormatParser.convert(fromFormat, toFormat, text);
        assertEquals(expResult, result);
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test
    public void testConvertJSONtoJSON() {
        System.out.println("convert");
        TextFormat fromFormat = TextFormat.JSON;
        TextFormat toFormat = TextFormat.JSON;
        String text = "{\n"
                + "  \"boolean\": true,\n"
                + "  \"array\": [\n"
                + "    1,\n"
                + "    2,\n"
                + "    3\n"
                + "  ],\n"
                + "  \"double\": 2,\n"
                + "  \"name\": \"JSON\",\n"
                + "  \"integer\": 1,\n"
                + "  \"nested\": {\"id\": 42}\n"
                + "}";

        String expResult = "{\n"
                + "  \"boolean\": true,\n"
                + "  \"array\": [\n"
                + "    1,\n"
                + "    2,\n"
                + "    3\n"
                + "  ],\n"
                + "  \"double\": 2,\n"
                + "  \"name\": \"JSON\",\n"
                + "  \"integer\": 1,\n"
                + "  \"nested\": {\"id\": 42}\n"
                + "}";

        String result = FormatParser.convert(fromFormat, toFormat, text);
        assertEquals(expResult, result);
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test
    public void testConvertXMLJSON() {
        System.out.println("convert");
        TextFormat fromFormat = TextFormat.XML;
        TextFormat toFormat = TextFormat.JSON;
        String expResult = "{\n"
                + "  \"boolean\": true,\n"
                + "  \"array\": [\n"
                + "    1,\n"
                + "    2,\n"
                + "    3\n"
                + "  ],\n"
                + "  \"double\": 2,\n"
                + "  \"name\": \"JSON\",\n"
                + "  \"integer\": 1,\n"
                + "  \"nested\": {\"id\": 42}\n"
                + "}";
        String text = "<boolean>true</boolean>\n"
                + "  <array>1</array>\n"
                + "  <array>2</array>\n"
                + "  <array>3</array>\n"
                + "  <double>2.0</double>\n"
                + "  <name>JSON</name>\n"
                + "  <integer>1</integer>\n"
                + "  <nested>\n"
                + "    <id>42</id>\n"
                + "  </nested>\n";

        String result = FormatParser.convert(fromFormat, toFormat, text);
        assertEquals(expResult, result);
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test
    public void testConvertToXMLExceptions() {
        System.out.println("convert");
        TextFormat fromFormat = TextFormat.XML;
        TextFormat toFormat = TextFormat.XML;
        String text = "<boolean>true</boolean>\n"
                + "  <array>1</array>\n"
                + "  <array>2</array>\n"
                + "  <array>3</array>\n"
                + "  <double>2.0</double>\n"
                + "  <name>JSON</name>\n"
                + "  <integer>1</integer>\n"
                + "  <nested>\n"
                + "    id>42</id>\n"
                + "  </nested>\n";

        String result = FormatParser.convert(fromFormat, toFormat, text);
        assertNull(result);
    }
    
    @Test
    public void testGetFormatIllegalArgument() {
        System.out.println("Invalid format");
        assertEquals(FormatParser.getTextFormat("notAformat"), TextFormat.NOTYPE);
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertNotSupportedToType() {
        FormatParser.convert(TextFormat.NOTYPE, TextFormat.NOTYPE, "");
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertNotSupportedFromType() {
        FormatParser.convert(TextFormat.NOTYPE, TextFormat.JSON, "");
    }

    /**
     * Test of convert method, of class FormatParser.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertNotSupportedFromType2() {
        FormatParser.convert(TextFormat.NOTYPE, TextFormat.XML, "");
    }
}
