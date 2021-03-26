/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http;

import java.util.HashMap;
import java.util.Map;
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
public class URITest {
    
    public URITest() {
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
     * Test of getUri method, of class URI.
     */
    @Test
    public void testGetUri() {
        System.out.println("getUri");
        URI instance = new URI("/metodo?query=value");
        String expResult = "/metodo?query=value";
        String result = instance.getUri();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuerylessURI method, of class URI.
     */
    @Test
    public void testGetQuerylessURI() {
        System.out.println("getQuerylessURI");
        URI instance = new URI("/metodo?query=value");
        String expResult = "/metodo";
        String result = instance.getQuerylessURI();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuerylessURI method, of class URI.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidURI() {
        System.out.println("testInvalidURI");
       new URI(" ");
    }
    
    /**
     * Test of getQuerylessURI method, of class URI.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNullURI() {
        System.out.println("testNullURI");
       new URI(null);
    }
    
    /**
     * Test of getQueryValues method, of class URI.
     */
    @Test
    public void testGetQueryValues() {
        System.out.println("getQueryValues");
        URI instance = new URI("/metodo?query=value");
        Map<String, String> expResult = new HashMap<>();
        expResult.put("query", "value");
        Map<String, String> result = instance.getQueryValues();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class URI.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        URI instance = new URI("/metodo?query=value");
        String expResult = "/metodo?query=value";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
