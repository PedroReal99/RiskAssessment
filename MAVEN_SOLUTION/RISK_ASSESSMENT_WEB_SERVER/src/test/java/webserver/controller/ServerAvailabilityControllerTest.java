/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webserver.http.HTTPConnector;
import webserver.threadController.ThreadController;
import webserver.threadController.ThreadTimeController;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ServerAvailabilityControllerTest {
    
    HTTPConnector h = new HTTPConnector(new Socket(), new ThreadController(1, 1), new ThreadTimeController(1));
    
    public ServerAvailabilityControllerTest() {
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
     * Test of serverLoadAndAvailibility method, of class ServerAvailabilityController.
     */
    @Test
    public void testServerLoadAndAvailibility() {
        System.out.println("serverLoadAndAvailibility");
        ServerAvailabilityController instance = new ServerAvailabilityController();
        List<String> expResult = new ArrayList<>();
        expResult.add("Available");
        expResult.add("1");
        expResult.add("1");
        List<String> result = instance.serverLoadAndAvailibility();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of obtainServerCurrentLoad method, of class ServerAvailabilityController.
     */
    @Test
    public void testObtainServerCurrentLoad() {
        System.out.println("obtainServerCurrentLoad");
        ServerAvailabilityController instance = new ServerAvailabilityController();
        int expResult = 1;
        int result = instance.obtainServerCurrentLoad();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainServerCurrentLoadPerMinute method, of class ServerAvailabilityController.
     */
    @Test
    public void testObtainServerCurrentLoadPerMinute() {
        System.out.println("obtainServerCurrentLoadPerMinute");
        ServerAvailabilityController instance = new ServerAvailabilityController();
        int expResult = 1;
        int result = instance.obtainServerCurrentLoadPerMinute();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtainServerCurrentAvailability method, of class ServerAvailabilityController.
     */
    @Test
    public void testObtainServerCurrentAvailability() {
        System.out.println("obtainServerCurrentAvailability");
        ServerAvailabilityController instance = new ServerAvailabilityController();
        boolean expResult = false;
        boolean result = instance.obtainServerCurrentAvailability();
        assertEquals(expResult, result);
        h = new HTTPConnector(new Socket(), new ThreadController(100, 29), new ThreadTimeController(1));
        expResult = true;
        result = instance.obtainServerCurrentAvailability();
        assertEquals(expResult, result);
        h = new HTTPConnector(new Socket(), new ThreadController(10, 30), new ThreadTimeController(1));
        expResult = true;
        result = instance.obtainServerCurrentAvailability();
        assertEquals(expResult, result);
    }

    /**
     * Test of convertAvailibiltyBooleanToString method, of class ServerAvailabilityController.
     */
    @Test
    public void testConvertAvailibiltyBooleanToString() {
        System.out.println("convertAvailibiltyBooleanToString");
        ServerAvailabilityController instance = new ServerAvailabilityController();
        String expResult = "Available";
        String result = instance.convertAvailibiltyBooleanToString();
        assertEquals(expResult, result);
        h = new HTTPConnector(new Socket(), new ThreadController(10, 30), new ThreadTimeController(1));
        expResult = "Not Available";
        result = instance.convertAvailibiltyBooleanToString();
        assertEquals(expResult, result);
    }
    
}
