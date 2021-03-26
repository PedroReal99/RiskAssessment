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
public class RoleTest {
    
    public RoleTest() {
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
     * Test of equals method, of class Role.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = Role.ADMIN;
        assertTrue(Role.ADMIN.equals(Role.ADMIN));
        assertFalse(Role.ADMIN.equals(null));
        assertTrue(Role.ADMIN.equals(obj));
        obj = Role.GUESS;
        assertFalse(Role.ADMIN.equals(obj));
        obj = new Object();
        assertFalse(Role.ADMIN.equals(obj));
    }

    /**
     * Test of toString method, of class Role.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "role= GUESS";
        String result = Role.GUESS.toString();
        assertEquals(expResult, result);
    }
    
}
