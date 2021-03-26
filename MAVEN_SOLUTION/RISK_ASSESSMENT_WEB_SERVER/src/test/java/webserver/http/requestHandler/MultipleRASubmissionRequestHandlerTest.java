/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webserver.http.Config;
import webserver.http.HTTPmessage;

/**
 *
 * @author Ricardo Branco
 */
public class MultipleRASubmissionRequestHandlerTest {
    
    public MultipleRASubmissionRequestHandlerTest() {
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
    public void testHandle_create() {
        System.out.println("handle");
        HTTPmessage request = new HTTPmessage();
        request.setContentFromFile("MultipleSubmissionTest.xml");
        request.setURI("/riskAssessment/cases/submit/multiple");
        request.setRequestMethod("PUT");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        MultipleRASubmissionRequestHandler instance = new MultipleRASubmissionRequestHandler();
        HTTPmessage expResult = new HTTPmessage();
        expResult.setResponseStatus(Config.CREATED);
        HTTPmessage result = instance.handle(request);
        assertEquals(expResult.getStatus(), result.getStatus());
    }
    
    /**
     * Test of handle method, of class MultipleRASubmissionRequestHandler.
     */
    @Test
    public void testHandle_badRequest() {
        System.out.println("handle");
        HTTPmessage request = new HTTPmessage();
        request.setContentFromFile("InvalidFile.xml");
        request.setURI("/riskAssessment/cases/submit/multiple");
        request.setRequestMethod("PUT");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        MultipleRASubmissionRequestHandler instance = new MultipleRASubmissionRequestHandler();
        HTTPmessage expResult = new HTTPmessage();
        expResult.setResponseStatus(Config.BAD_REQUEST);
        HTTPmessage result = instance.handle(request);
        assertEquals(expResult.getStatus(), result.getStatus());
    }
    
    /**
     * Test of handle method, of class MultipleRASubmissionRequestHandler.
     */
    @Test
    public void testHandle_internalError() {
        System.out.println("handle");
        HTTPmessage request = new HTTPmessage();
        request.setContentFromFile("MultipleSubmissionTest_Errors.xml");
        request.setURI("/riskAssessment/cases/submit/multiple");
        request.setRequestMethod("PUT");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        MultipleRASubmissionRequestHandler instance = new MultipleRASubmissionRequestHandler();
        HTTPmessage expResult = new HTTPmessage();
        expResult.setResponseStatus(Config.INTERNAL_ERROR);
        HTTPmessage result = instance.handle(request);
        assertEquals(expResult.getStatus(), result.getStatus());
    }
    
}
