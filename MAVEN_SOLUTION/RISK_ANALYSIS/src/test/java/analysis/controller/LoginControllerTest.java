/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import core.domain.User.Email;
import core.domain.User.Password;
import core.domain.User.Role;
import core.domain.User.User;
import core.persistence.PersistenceContext;
import core.persistence.UserRepository;
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
public class LoginControllerTest {
    
    User u1 = new User(new Email("admin1@gmail.com") , new Password("admin1"), Role.ADMIN);
    User u2 = new User(new Email("ar1@gmail.com") ,new Password("ar1") , Role.RISK_ANALIST);
    User u3 = new User(new Email("guess@gmail.com"), new Password("guess") , Role.GUESS);
    
    public LoginControllerTest() {
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
     * Test of loginRiskAnalysis method, of class LoginController.
     */
    @Test
    public void testLoginRiskAnalysis() {
        System.out.println("loginRiskAnalysis");
        LoginController instance = new LoginController();
        instance.obtainUserRepository().save(u1);
        instance.obtainUserRepository().save(u2);
        instance.obtainUserRepository().save(u3);
        assertFalse(instance.loginRiskAnalysis("test1^", "test1@gmail.com")); //8
        assertFalse(instance.loginRiskAnalysis("test1^", "test1^")); //16
        assertFalse(instance.loginRiskAnalysis("test1", "test1")); //6
        assertFalse(instance.loginRiskAnalysis("test1^@gmail.com", "test1^@gmail.com")); //7
        assertFalse(instance.loginRiskAnalysis("", "test1@gmail.com")); //18
        assertFalse(instance.loginRiskAnalysis("test1@gmail.com", "")); //17
        assertFalse(instance.loginRiskAnalysis("test1@gmail.com", "test2@gmail.com")); //14
        assertTrue(instance.loginRiskAnalysis("test1@gmail.com", "test1@gmail.com")); //5
        assertFalse(instance.loginRiskAnalysis("test1@gmail.com", "test1")); //9
        assertFalse(instance.loginRiskAnalysis("ar1@gmail.com", "ar2")); //10
        assertFalse(instance.loginRiskAnalysis("guess@gmail.com", "guess")); //11
        assertTrue(instance.loginRiskAnalysis("ar1@gmail.com", "ar1")); //13
        assertTrue(instance.loginRiskAnalysis("admin1@gmail.com", "admin1")); //12
    }

    /**
     * Test of verifyGuestLogin method, of class LoginController.
     */
    @Test
    public void testVerifyGuestLogin() {
        System.out.println("verifyGuestLogin");
        LoginController instance = new LoginController();
        assertTrue(instance.verifyGuestLogin("test1@gmail.com", "test1@gmail.com"));
        assertFalse(instance.verifyGuestLogin("test1@gmail.com", "test2@gmail.com"));        
    }
    
    /**
     * Test of verifyGuestLogin method, of class LoginController.
     */
    @Test
    public void testVerifyRegistedUserLogin() {
        System.out.println("verifyRegistedUserLogin");
        LoginController instance = new LoginController();
        assertTrue(instance.verifyRegistedUserLogin(u1, new Email("admin1@gmail.com") , new Password("admin1")));
        assertFalse(instance.verifyRegistedUserLogin(u1, new Email("admin1@gmail.com") , new Password("admin2")));   
        assertFalse(instance.verifyRegistedUserLogin(u3, new Email("guess@gmail.com") , new Password("guess")));      
    }

    /**
     * Test of obtainUserByEmail method, of class LoginController.
     */
    @Test
    public void testObtainUserByEmail() {
        System.out.println("obtainUserByEmail");
        Email email = new Email("admin1@gmail.com");
        LoginController instance = new LoginController();
        User expResult = new User(new Email("admin1@gmail.com") , new Password("admin1"), Role.ADMIN);
        instance.obtainUserRepository().save(u1);
        User result = instance.obtainUserByEmail(email);
        assertEquals(expResult, result);
    }
    
}
