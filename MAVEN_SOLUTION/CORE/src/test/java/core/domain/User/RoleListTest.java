/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.User;

import java.util.ArrayList;
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
public class RoleListTest {
    
    public RoleListTest() {
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
     * Test of addRoleToUser method, of class RoleList.
     */
    @Test
    public void testAddRoleToUser() {
        System.out.println("addRoleToUser");
        Role r = Role.ADMIN;
        RoleList instance = new RoleList(new ArrayList<>());
        ArrayList<Role> role = new ArrayList<>();
        role.add(r);
        RoleList expected = new RoleList(role);
        instance.addRoleToUser(r);
        assertEquals(instance,expected);
    }

    /**
     * Test of equals method, of class RoleList.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.ADMIN);
        RoleList expected = new RoleList(role);
        
        Object obj = expected;
        assertTrue(expected.equals(expected));
        assertFalse(expected.equals(null));
        assertTrue(expected.equals(obj));
        
        ArrayList<Role> role1 = new ArrayList<>();
        role1.add(Role.ADMIN);
        role1.add(Role.GUESS);
        RoleList expected1 = new RoleList(role1);
        
        obj = expected1;
        assertFalse(expected.equals(obj));
        obj = new Object();
        assertFalse(expected1.equals(obj));
    }

    /**
     * Test of toString method, of class RoleList.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.GUESS);
        RoleList expected = new RoleList(role);
        String expResult = "[role= GUESS]";
        String result = expected.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of verifyRole method, of class RoleList.
     */
    @Test
    public void testVerifyRole() {
        System.out.println("verifyRole");
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.ADMIN);
        role.add(Role.GUESS);
        RoleList expected = new RoleList(role);
        assertTrue(expected.verifyRole(Role.ADMIN));
        assertFalse(expected.verifyRole(Role.RISK_ANALIST));
    }
    
}
