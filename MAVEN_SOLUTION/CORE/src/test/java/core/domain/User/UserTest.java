/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.User;

import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasco_Rodrigues
 */
public class UserTest {
    
    User u = new User(new Email("test1@gmail.com"), new Password("test1"), Role.ADMIN);
    
    public UserTest() {
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

    public int hashCodeCalculator() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(u.identity());
        return hash;
    }
    
    @Test
    public void testHashCode() {
        assertEquals(u.hashCode(), hashCodeCalculator());
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new User(new Email("test1@gmail.com"), new Password("test1"), Role.ADMIN);
        assertTrue(u.equals(u));
        assertFalse(u.equals(null));
        assertTrue(u.equals(obj));
        obj = new User(new Email("test11@gmail.com"), new Password("test1"), Role.ADMIN);
        assertFalse(u.equals(obj));
        obj = new Object();
        assertFalse(u.equals(obj));
    }

    /**
     * Test of toString method, of class User.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "User{email=email= test1@gmail.com, password=core.domain.User.Password@6924fde, roles=[role= ADMINISTRATOR]}";
        String result = u.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of sameAs method, of class User.
     */
    @Test
    public void testSameAs() {
        System.out.println("sameAs");
        Object obj = new User(new Email("test1@gmail.com"), new Password("test1"), Role.ADMIN);
        assertTrue(u.sameAs(u));
        assertFalse(u.sameAs(null));
        assertTrue(u.sameAs(obj));
        obj = new User(new Email("test11@gmail.com"), new Password("test1"), Role.ADMIN);
        assertFalse(u.sameAs(obj));
        obj = new Object();
        assertFalse(u.sameAs(obj));
    }

    /**
     * Test of identity method, of class User.
     */
    @Test
    public void testIdentity() {
        System.out.println("identity");
        Email expResult = new Email("test1@gmail.com");
        Email result = u.identity();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of verifyUserPassword method, of class User.
     */
    @Test
    public void testVerifyUserPassword() {
        System.out.println("verifyUserPassword");
        assertTrue(u.verifyUserPassword(new Password("test1")));
        assertFalse(u.verifyUserPassword(new Password("test12")));
    }
    
    /**
     * Test of identity method, of class User.
     */
    @Test
    public void testVerifyRole() {
        System.out.println("verifyRole");
        assertTrue(u.verifyRole(Role.ADMIN));
        assertFalse(u.verifyRole(Role.EXTERNAL_SYSTEM));
    }
}
