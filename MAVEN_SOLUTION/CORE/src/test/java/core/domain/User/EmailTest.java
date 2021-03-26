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
 * @author Vasco Rodrigues
 */
public class EmailTest {
    
    Email e = new Email("test1@gmail.com");
    
    public EmailTest() {
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
        new Email("test2@test.com");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailOnlyOneSpacesNotAllowed() {
        new Email(" ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailNullParameterNotAllowed() {
        new Email(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailEmptyParameterNotAllowed() {
        new Email("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithoutDomainNotAllowed() {
        new Email("test1@.com");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithNumbersOnDomainNotAllowed() {
        new Email("test1@gmail4.com");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithoutSomethingAfterDomainNotAllowed() {
        new Email("test1@gmail.");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithCapitalLettersOnDomainNotAllowed() {
        new Email("test1@Gmail.com");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithCapitalLetterAfterDomainNotAllowed() {
        new Email("test1@gmail.Com");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithNumbersAfterDomainNotAllowed() {
        new Email("test1@gmail.c1m");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithPunctuationNotAllowed() {
        new Email("te^@gmail.com");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithPunctuationOnDomainNotAllowed() {
        new Email("t@g^mail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureEmailWithPunctuationAfterDomainNotAllowed() {
        new Email("te@gmail.com^");
    }

    /**
     * Test of equals method, of class Email.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Email("test1@gmail.com");
        assertTrue(e.equals(e));
        assertFalse(e.equals(null));
        assertTrue(e.equals(obj));
        obj = new Email("test2@gmail.com");
        assertFalse(e.equals(obj));
        obj = new Object();
        assertFalse(e.equals(obj));
    }

    /**
     * Test of toString method, of class Email.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "email= test1@gmail.com";
        String result = e.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Email.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Email instance = new Email();
        int expResult = 0;
        int result = e.compareTo(e);
        assertEquals(expResult, result);
    }
    
}
