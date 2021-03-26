/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.MIN_PRIORITY;
import static java.lang.Thread.NORM_PRIORITY;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import webserver.threadController.ThreadController;
import webserver.threadController.ThreadTimeController;

/**
 *
 * @author Vasco_Rodrigues
 */

@RunWith(ConcurrentTestRunner.class)
public class HTTPConnectorTest {
    
    List<HTTPConnector> l = new ArrayList<>();
    
    public HTTPConnectorTest() {
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
     * Test of run method, of class HTTPConnector.
     */
    //@Test
    @ThreadCount(31)
    public void testRun() {
        System.out.println("run");
        ThreadTimeController tt = new ThreadTimeController(60000);
        tt.addThreadExecutionTime(new Date());
        HTTPConnector instance = new HTTPConnector(new Socket(), new ThreadController(2, 2), tt);
        instance.run();
    }

    /**
     * Test of verifyServerLoad method, of class HTTPConnector.
     */
    //@Test(expected = InterruptedException.class)
    @ThreadCount(5)
    public void testVerifyServerLoadExceptions() throws Exception {
        System.out.println("verifyServerLoadExceptions");
        ThreadTimeController tt = new ThreadTimeController(60000);
        for(int i=0; i<32; i++) {
            tt.addThreadExecutionTime(new Date());
        }
        HTTPConnector instance = new HTTPConnector(new Socket(), new ThreadController(2, 2), tt);
        instance.verifyServerLoad();  
    }
    
    /**
     * Test of verifyServerLoad method, of class HTTPConnector.
     */
    //@Test
    @ThreadCount(5)
    public void testVerifyServerLoad() throws InterruptedException {
        System.out.println("verifyServerLoad");
        ThreadTimeController tt2 = new ThreadTimeController(60000);
        for(int i=0; i<10; i++) {
            tt2.addThreadExecutionTime(new Date());
        }
        HTTPConnector instance2 = new HTTPConnector(new Socket(), new ThreadController(2, 2), tt2);
        instance2.verifyServerLoad(); 
    }

    /**
     * Test of setPriorityForThreads method, of class HTTPConnector.
     */
    @Test
    @ThreadCount(5)
    public void testSetPriorityForThreadsMax() {
        System.out.println("setPriorityForThreadsMax");
        String func = "/riskAssessment/currentload";
        HTTPConnector.setPriorityForThreads(func);
        assertEquals(Thread.currentThread().getPriority(), MAX_PRIORITY);
    }
    
    /**
     * Test of setPriorityForThreads method, of class HTTPConnector.
     */
    @Test
    @ThreadCount(5)
    public void testSetPriorityForThreadsMin() {
        System.out.println("setPriorityForThreadsMin");
        String func = "/riskAssessment/cases/submit/multiple";
        HTTPConnector.setPriorityForThreads(func);
        assertEquals(Thread.currentThread().getPriority(), MIN_PRIORITY);
    }
    
    /**
     * Test of setPriorityForThreads method, of class HTTPConnector.
     */
    @Test
    @ThreadCount(5)
    public void testSetPriorityForThreadsNormal() {
        System.out.println("setPriorityForThreadsNorm");
        String func = "/riskAssessment/compare";
        HTTPConnector.setPriorityForThreads(func);
        assertEquals(Thread.currentThread().getPriority(), NORM_PRIORITY);
    }

    /**
     * Test of updateCurrentLoadPerMinute method, of class HTTPConnector.
     */
    //@Test
    public void testUpdateCurrentLoadPerMinute() {
        System.out.println("updateCurrentLoadPerMinute");
        HTTPConnector h = new HTTPConnector(new Socket(), new ThreadController(2, 2), new ThreadTimeController(1));
        HTTPConnector.updateCurrentLoadPerMinute();
        
    }

    /**
     * Test of obtainThreadController method, of class HTTPConnector.
     */
    @Test
    @ThreadCount(2)
    public void testObtainThreadController() {
        System.out.println("obtainThreadController");
        HTTPConnector h = new HTTPConnector(new Socket(), new ThreadController(2, 2), new ThreadTimeController(1));
        ThreadController expResult = new ThreadController(2, 2);
        ThreadController result = HTTPConnector.obtainThreadController();
        assertEquals(expResult.getLoad(), result.getLoad());
        assertEquals(expResult.getLoadPerMinute(), result.getLoadPerMinute());
    }
    
}
