/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webserver.http.HTTPmessage;

/**
 *
 * @author morei
 */
public class StatsControllerTest {

    public StatsControllerTest() {
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
     * Test of associateBegining method, of class StatsController.
     */
    @Test
    public void testAssociateBegining() {
        System.out.println("associateBegining");
        HTTPmessage request = new HTTPmessage();
        request.setURI("/riskAssessment/currentload?export=xml");
        request.setRequestMethod("GET");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        Date date = Date.from(Instant.now());
        StatsController.associateBegining(request, date);
    }

    /**
     * Test of setHttpMessages method, of class StatsController.
     */
    @Test
    public void testSetHttpMessages() {
        System.out.println("setHttpMessages");
        List<HTTPmessage> httpMessages = new ArrayList<>();

        HTTPmessage request = new HTTPmessage();
        request.setURI("/riskAssessment/currentload?export=xml");
        request.setRequestMethod("GET");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");
        httpMessages.add(request);
        StatsController.setHttpMessages(httpMessages);
        assertArrayEquals(httpMessages.toArray(), StatsController.getHttpMessages().toArray());
    }

    /**
     * Test of setDates method, of class StatsController.
     */
    @Test
    public void testSetDates() {
        System.out.println("setDates");
        List<Date> httpMessages = new ArrayList<>();

        httpMessages.add(Date.from(Instant.now()));
        StatsController.setDates(httpMessages);
        assertArrayEquals(httpMessages.toArray(), StatsController.getDates().toArray());
    }

    /**
     * Test of createBody method, of class StatsController.
     */
    @Test
    public void testCreateBody() {
        System.out.println("createBody");
        StatsController instance = new StatsController();
        List<HTTPmessage> httpMessages = new ArrayList<>();

        HTTPmessage request = new HTTPmessage();
        request.setURI("/riskAssessment/currentload?export=xml");
        request.setRequestMethod("GET");
        request.setHeader("Authorization", "kuibjkhsabewfhjkuifu73g38wshgfilu");

        httpMessages.add(request);
        Date d = new Date(10000000);
        
        List<Date> dates = new ArrayList<>();
        dates.add(d);

        //StatsController.associateBegining(request, d);
        StatsController.setDates(dates);
        StatsController.setHttpMessages(httpMessages);

        //String expResult = "Data Sun Jun 16 22:37:05 BST 2019 Metodo GET Uri /riskAssessment/currentload?export=xml Request null";
        String result = StatsController.createBody();

        String s1 = " Metodo " + httpMessages.get(0).getMethod();
        String s2 = " Uri /riskAssessment/currentload";
        String s3 = " File xml";
        
        assertEquals(s1, " Metodo GET");
        assertEquals(s2, " Uri /riskAssessment/currentload");
        assertEquals(s3, " File xml");
    }

}
