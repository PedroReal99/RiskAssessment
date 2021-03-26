/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.threadController;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
/**
 *
 * @author Vasco_Rodrigues
 */
@RunWith(ConcurrentTestRunner.class)
public class ThreadControllerTest {
    
    ThreadController instance;
            
    ThreadController instance1;   
    
    ThreadController instance2;
    
    ThreadController instance3;
    
    public ThreadControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ThreadController(4, 4);
        instance1 = new ThreadController(0, 0);
        instance2 = new ThreadController(2, 2);
        instance3 = new ThreadController(2, 2);
    }
    
    @After
    public void tearDown() {
        assertEquals(20, instance1.getLoad());
        assertEquals(0, instance.getLoad());
        assertEquals(20, instance2.getLoadPerMinute());
        assertEquals(2, instance2.getLoad());
        assertEquals(2, instance2.getLoad());
    }

    /**
     * Test of addLoad method, of class ThreadController.
     */
    @Test
    @ThreadCount(20)
    public void testAddLoad() {
        System.out.println("addLoad");
        instance1.addLoad();
    }

    /**
     * Test of removeLoad method, of class ThreadController.
     */
    @Test
    @ThreadCount(4)
    public void testRemoveLoad() {
        System.out.println("removeLoad");
        instance.removeLoad();
    }

    /**
     * Test of updateLoadPerMinute method, of class ThreadController.
     */
    @Test
    @ThreadCount(3)
    public void testUpdateLoadPerMinute() {
        System.out.println("updateLoadPerMinute");
        int i = 20;
        instance2.updateLoadPerMinute(i);
    }   
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureNegativeValuesOrZeroNotAllowed() {
        new ThreadController(0, 1);
        new ThreadController(1, 0);
        new ThreadController(-1, 1);
        new ThreadController(1, -1);
    }
}
