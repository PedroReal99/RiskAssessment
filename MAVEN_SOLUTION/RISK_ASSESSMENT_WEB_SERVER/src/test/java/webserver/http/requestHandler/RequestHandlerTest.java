/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webserver.http.Config;
import webserver.http.HTTPmessage;
import webserver.utils.FormatParser;

/**
 *
 * @author CarloS
 */
public class RequestHandlerTest {
    
    public RequestHandlerTest() {
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
     * Test of handle method, of class RequestHandler.
     */
    @Test
    public void testHandle() {
        System.out.println("handle");
        HTTPmessage request = null;
        RequestHandler instance = new RequestHandlerImpl();
        HTTPmessage expResult = null;
        HTTPmessage result = instance.handle(request);
        assertEquals(expResult, result);
    }

    /**
     * Test of findKey method, of class RequestHandler.
     */
    @Test
    public void testFindKeyTrue() {
        System.out.println("findKey");
        String givenKey = "kuibjkhsabewfhjkuifu73g38wshgfilu";
        RequestHandler instance = new RequestHandlerImpl();
        boolean expResult = true;
        boolean result = instance.findKey(givenKey);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of findKey method, of class RequestHandler.
     */
    @Test
    public void testFindKeyFalse() {
        System.out.println("findKey");
        String givenKey = "kuibjkhsabewfhjkuifu73g38w2shgfilu";
        RequestHandler instance = new RequestHandlerImpl();
        boolean expResult = false;
        boolean result = instance.findKey(givenKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of handleHttpExceptions method, of class RequestHandler.
     */
    @Test
    public void testHandleHttpExceptionsUnauthorized() {
        System.out.println("HandleHttpExceptionsUnauthorized");
        HTTPmessage request=new HTTPmessage();
        request.setHeader("Authorization", "");
        HTTPmessage response = new HTTPmessage();
        RequestHandler instance = new RequestHandlerImpl();
        Map<String, String> expResult = null;
        Map<String, String> result = instance.handleHttpExceptions(request, response);
        assertEquals(expResult, result);
        assertEquals(Config.UNAUTHORIZED,response.getStatus());
    }

    /**
     * Test of handleHttpExceptions method, of class RequestHandler.
     */
    @Test
    public void testHandleHttpExceptionsBadRequest() {
        System.out.println("HandleHttpExceptionsBadRequest");
        HTTPmessage request=new HTTPmessage();
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        HTTPmessage response = new HTTPmessage();
        RequestHandler instance = new RequestHandlerImpl();
        Map<String, String> expResult = null;
        Map<String, String> result = instance.handleHttpExceptions(request, response);
        assertEquals(expResult, result);
        assertEquals(Config.BAD_REQUEST,response.getStatus());
    }
    
    /**
     * Test of handleHttpExceptions method, of class RequestHandler.
     */
    @Test
    public void testHandleHttpExceptionsSuccess() {
        System.out.println("HandleHttpExceptionsSuccess");
        HTTPmessage request=new HTTPmessage();
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        request.setContent("not a content",HTTPmessage.KNOWN_FILE_EXT.get(".xml"));
        HTTPmessage response = new HTTPmessage();
        RequestHandler instance = new RequestHandlerImpl();
        Map<String, String> expResult = new HashMap<>();
        expResult.put("key", request.getHeader().get("Authorization"));
        expResult.put("format", FormatParser.getTextFormat(request.getContentType()).name());
        Map<String, String> result = instance.handleHttpExceptions(request, response);
        assertEquals(expResult, result);
    }
    
    public class RequestHandlerImpl implements RequestHandler {

        @Override
        public HTTPmessage handle(HTTPmessage request) {
            return null;
        }
    }
    
}
