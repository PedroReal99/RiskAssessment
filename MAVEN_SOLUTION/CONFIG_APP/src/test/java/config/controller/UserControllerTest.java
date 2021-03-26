/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.controller;

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
public class UserControllerTest {
    
    public UserControllerTest() {
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
     * Test of createUser method, of class UserController.
     */
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        String email = "^@gmail.com";
        String password = "ar1";
        Role role = Role.RISK_ANALIST;
        UserController instance = new UserController();
        User expResult = null;
        User result = instance.createUser(email, password, role);
        assertEquals(expResult, result);
        email = "ar1@gmail.com";
        result = instance.createUser(email, password, role);
        expResult = new User(new Email("ar1@gmail.com"), new Password("ar1"), Role.RISK_ANALIST);
        assertEquals(expResult, result);
    }

    /**
     * Test of createUr method, of class UserController.
     */
    @Test
    public void testCreateUr() {
        System.out.println("createUr");
        String email = "^@gmail.com";
        String password = "test1";
        Role role = Role.ADMIN;
        UserController instance = new UserController();
        User expResult = null;
        User result = instance.createUr(email, password, role);
        assertEquals(expResult, result);
        email = "test1@gmail.com";
        result = instance.createUr(email, password, role);
        expResult = new User(new Email("test1@gmail.com"), new Password("test1"), Role.ADMIN);
        assertEquals(expResult, result);
    }

    /**
     * Test of persistUser method, of class UserController.
     */
    @Test
    public void testPersistUser() {
        System.out.println("persistUser");
        User u = new User(new Email("admin1@gmail.com") , new Password("admin1"), Role.ADMIN);
        UserController instance = new UserController();
        instance.persistUser(u);
        User per = PersistenceContext.repositories().UserRepository().obtainUserByEmail(new Email("admin1@gmail.com"));
        assertEquals(u, per);
    }
    
}
