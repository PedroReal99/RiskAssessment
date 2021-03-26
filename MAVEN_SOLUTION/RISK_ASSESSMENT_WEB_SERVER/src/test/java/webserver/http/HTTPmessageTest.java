/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class HTTPmessageTest {

    public HTTPmessageTest() {
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
     * Test of setResponseStatus method, of class HTTPmessage.
     */
    @Test
    public void testSetResponseStatus() {
        System.out.println("setResponseStatus");
        String sT = "";
        HTTPmessage instance = new HTTPmessage();
        instance.setResponseStatus(sT);
        assertEquals(sT, instance.getStatus());
    }

    /**
     * Test of setContent method, of class HTTPmessage.
     */
    @Test
    public void testSetContent() {
        System.out.println("setContent");
        String cnt = "test Content";
        String cType = "text/plain";
        HTTPmessage instance = new HTTPmessage();
        instance.setContent(cnt, cType);
        assertEquals(cnt, instance.getContentAsString());
        Assert.assertArrayEquals(cnt.getBytes(), instance.getContent());
        assertEquals(cType, instance.getContentType());
    }

    /**
     * Test of setRequestMethod method, of class HTTPmessage.
     */
    @Test
    public void testSetRequestMethod() {
        System.out.println("setRequestMethod");
        String m = "PUT";
        HTTPmessage instance = new HTTPmessage();
        instance.setRequestMethod(m);
        assertEquals(m, instance.getMethod());
    }

    /**
     * Test of send method, of class HTTPmessage.
     */
    @Test
    public void testSendFails() throws Exception {
        System.out.println("testSendFails");
        DataOutputStream out = null;
        HTTPmessage instance = new HTTPmessage();
        boolean expResult = false;
        boolean result = instance.send(out);
        assertEquals(expResult, result);
        instance.setRequestMethod("PUT");
        result = instance.send(out);
        assertEquals(expResult, result);
        instance.setResponseStatus(null);
        result = instance.send(out);
        assertEquals(expResult, result);
    }

    /**
     * Test of send method, of class HTTPmessage.
     */
    @Test
    public void testSendRequest() throws Exception {
        System.out.println("testSendRequest");
        HTTPmessage instance = new HTTPmessage();
        instance.setRequestMethod("PUT");
        instance.setURI("/pedido?testado=comMuitaQualidade");
        instance.setHeader("Authorization", "umaKey");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(os);
        instance.send(out);

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        DataInputStream in = new DataInputStream(is);
        HTTPmessage received = new HTTPmessage(in);
        
        assertEquals(instance.getMethod(), received.getMethod());
        assertEquals(instance.getURI().toString(), received.getURI().toString());
        assertEquals(instance.getHeader().get("Connection"), received.getHeader().get("Connection"));
    }

    /**
     * Test of send method, of class HTTPmessage.
     */
    @Test
    public void testSendResponse() throws Exception {
        System.out.println("testSendResponse");

        HTTPmessage instance = new HTTPmessage();
        instance.setHeader("Authorization", "umaKey");
        instance.setResponseStatus("404");
        instance.setContentFromFile("SubmissionTest.xml");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(os);
        instance.send(out);

        InputStream is = new ByteArrayInputStream(os.toByteArray());
        DataInputStream in = new DataInputStream(is);
        HTTPmessage received = new HTTPmessage(in);

        assertEquals(instance.getStatus(), received.getStatus());
        assertEquals(instance.getContentAsString().substring(0, 333), received.getContentAsString().substring(0, 333));
    }

    /**
     * Test of hasContent method, of class HTTPmessage.
     */
    @Test
    public void testHasContent() {
        System.out.println("hasContent");
        HTTPmessage instance = new HTTPmessage();
        boolean expResult = false;
        boolean result = instance.hasContent();
        assertEquals(expResult, result);
    }
}
