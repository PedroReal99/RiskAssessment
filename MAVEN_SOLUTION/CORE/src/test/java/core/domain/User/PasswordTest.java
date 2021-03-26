/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasco Rodrigues
 */
public class PasswordTest {
    
    Password p = new Password("admin1");
    
    public PasswordTest() {
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
    
    @Test
    public void ensureEmailIsValid() {
        new Password("admin1");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordEmptyNotAllowed() {
        new Password("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordNullNotAllowed() {
        new Password(null);
    }

    /**
     * Test of equals method, of class Password.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Password("admin1");
        assertTrue(p.equals(p));
        assertFalse(p.equals(null));
        assertTrue(p.equals(obj));
        obj = new Password("admin2");
        assertFalse(p.equals(obj));
        obj = new Object();
        assertFalse(p.equals(obj));
    }
    
}
