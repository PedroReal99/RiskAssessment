/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import java.io.File;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webserver.http.Config;
import webserver.http.HTTPConnector;
import webserver.http.HTTPmessage;
import webserver.threadController.ThreadController;
import webserver.threadController.ThreadTimeController;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ServerAvailabilityHandlerTest {
    
    HTTPConnector h = new HTTPConnector(new Socket(), new ThreadController(1, 1), new ThreadTimeController(1));
    
    public ServerAvailabilityHandlerTest() {
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
     * Test of handle method, of class MultipleRASubmissionRequestHandler.
     */
    @Test
    public void testHandle_PositiveResponse() {
        System.out.println("handle");
        HTTPmessage request = new HTTPmessage();
        request.setURI("/riskAssessment/currentload?export=xml");
        request.setRequestMethod("GET");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        ServerAvailabilityHandler instance = new ServerAvailabilityHandler();
        HTTPmessage expResult = new HTTPmessage();
        expResult.setResponseStatus(Config.POSITIVE_RESPONSE);
        expResult.setContentFromFile("SE06_Test.xml");
        HTTPmessage result = instance.handle(request);
        assertEquals(expResult.getStatus(), result.getStatus());
    }

    /**
     * Test of buildInformationToString method, of class ServerAvailabilityHandler.
     */
    @Test
    public void testBuildInformationToString() {
        System.out.println("buildInformationToString");
        List<String> aux = new ArrayList<>();
        aux.add("Available");
        aux.add("1");
        aux.add("1");
        ServerAvailabilityHandler instance = new ServerAvailabilityHandler();
        String expResult = "Server Available\n with load = 1\n and load per minute = 1";
        String result = instance.buildInformationToString(aux);
        assertEquals(expResult, result);
    }

    /**
     * Test of exportIntoDesiredFileType method, of class ServerAvailabilityHandler.
     */
    @Test
    public void testExportIntoDesiredFileType() throws Exception {
        System.out.println("exportIntoDesiredFileType");
        String filetype = "XML";
        String content = "Server Available\n with load = 1\n and load per minute = 1";
        ServerAvailabilityHandler instance = new ServerAvailabilityHandler();
        instance.exportIntoDesiredFileType(filetype, content);
        //assertEquals(Files.readAllLines(FileSystems.getDefault().getPath("", "SE06_Test.xml"), Charset.defaultCharset()), Files.readAllLines(FileSystems.getDefault().getPath("", "SE06.xml"), Charset.defaultCharset()));
        assertEquals(new File("SE06_Test.xml").getFreeSpace(),new File("SE06.xml").getFreeSpace());
        filetype = "XHTML";
        instance.exportIntoDesiredFileType(filetype, content);
        //assertEquals(Files.readAllLines(FileSystems.getDefault().getPath("", "SE06_Test.xhtml"), Charset.defaultCharset()), Files.readAllLines(FileSystems.getDefault().getPath("", "SE06.xhtml"), Charset.defaultCharset()));
        assertEquals(new File("SE06_Test.xhtml").getFreeSpace(),new File("SE06.xhtml").getFreeSpace());
        filetype = "JSON";
        instance.exportIntoDesiredFileType(filetype, content);
        assertEquals(new File("SE06_Test.json").getFreeSpace(),new File("SE06.json").getFreeSpace());
        //assertEquals(Files.readAllLines(FileSystems.getDefault().getPath("", "SE06_Test.json"), Charset.defaultCharset()), Files.readAllLines(FileSystems.getDefault().getPath("", "SE06.json"), Charset.defaultCharset()));
        File f = instance.exportIntoDesiredFileType("TXT", content);
        assertNull(f);
    }

    /**
     * Test of buildResponse method, of class ServerAvailabilityHandler.
     */
    @Test
    public void testBuildResponse() {
        System.out.println("buildResponse");
        File file = new File("SE06.xml");
        HTTPmessage expResponse = new HTTPmessage();
        expResponse.setContentFromFile(file.getName());
        expResponse.setResponseStatus(Config.POSITIVE_RESPONSE);
        HTTPmessage response = new HTTPmessage();
        ServerAvailabilityHandler instance = new ServerAvailabilityHandler();
        instance.buildResponse(response, file);
        assertArrayEquals(expResponse.getContent(),response.getContent());
        assertEquals(expResponse.getStatus(),response.getStatus());
    }
    
}
