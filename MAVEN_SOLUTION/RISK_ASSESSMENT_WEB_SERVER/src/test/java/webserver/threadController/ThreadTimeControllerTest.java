/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.threadController;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.MIN_PRIORITY;
import java.util.Date;
import java.util.Random;
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
public class ThreadTimeControllerTest {
    
    ThreadTimeController instance;
            
    ThreadTimeController instance1;  
    
    ThreadTimeController instance2; 

    
    public ThreadTimeControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ThreadTimeController(1);
        instance1 = new ThreadTimeController(1);
        instance2 = new ThreadTimeController(1);
        instance1.addThreadExecutionTime(new Date(System.currentTimeMillis() - new Random().nextInt(10000)));
        instance1.addThreadExecutionTime(new Date(System.currentTimeMillis() - new Random().nextInt(10000)));
        instance1.addThreadExecutionTime(new Date(System.currentTimeMillis() - new Random().nextInt(10000)));
        instance1.addThreadExecutionTime(new Date(System.currentTimeMillis() - new Random().nextInt(10000)));
        instance1.addThreadExecutionTime(new Date(System.currentTimeMillis() - new Random().nextInt(10000)));
        instance2.addThreadExecutionTime(new Date(System.currentTimeMillis() + new Random().nextInt(10000)));
        instance2.addThreadExecutionTime(new Date(System.currentTimeMillis() + new Random().nextInt(10000)));
        instance2.addThreadExecutionTime(new Date(System.currentTimeMillis() + new Random().nextInt(10000)));
        instance2.addThreadExecutionTime(new Date(System.currentTimeMillis() + new Random().nextInt(10000)));
        instance2.addThreadExecutionTime(new Date(System.currentTimeMillis() + new Random().nextInt(10000)));
    }
    
    @After
    public void tearDown() {
        assertEquals(5, instance.numThreadsRunning());
        assertFalse(instance2.threads_execution_time.isEmpty());
        assertTrue(instance1.threads_execution_time.isEmpty());
    }

    /**
     * Test of addThreadExecutionTime method, of class ThreadTimeController.
     */
    @Test
    @ThreadCount(5)
    public void testAddThreadExecutionTime() {
        System.out.println("addThreadExecutionTime");
        Thread.currentThread().setPriority(MAX_PRIORITY);
        instance.addThreadExecutionTime(new Date(System.currentTimeMillis() + new Random().nextInt(10000))); 
    }

    /**
     * Test of verifyThreadsDate method, of class ThreadTimeController.
     */
    @Test
    @ThreadCount(5)
    public void testVerifyThreadsDate() throws InterruptedException {
        System.out.println("verifyThreadsDateRemove");
        Thread.currentThread().setPriority(MIN_PRIORITY);
        instance2.verifyThreadsDate();     
    }
    
    /**
     * Test of verifyThreadsDate method, of class ThreadTimeController.
     */
    @Test
    @ThreadCount(5)
    public void testVerifyThreadsDateToRemove() throws InterruptedException {
        System.out.println("verifyThreadsDate");
        Thread.currentThread().setPriority(MIN_PRIORITY);
        instance1.verifyThreadsDate();     
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureNegativeValuesOrZeroNotAllowed() {
        new ThreadTimeController(0);
        new ThreadTimeController(-1);
    }
    
}
