/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro
 */
public class TimePassedTest {

    public TimePassedTest() {
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
     * Test of TimePassed method, of class TimePassed.
     */
    @Test
    public void testTimePassed() throws Exception {
        System.out.println("TimePassed");
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String cd = formatter.format(now);
        boolean flag = false;
        //String expResult = "01:00:00";
        String result = TimePassed.TimePassed(cd);
        if (!result.equalsIgnoreCase("")) {
            if (result.equalsIgnoreCase("01:00:00") || result.equalsIgnoreCase("01:00:01") || result.equalsIgnoreCase("0:00:00") || result.equalsIgnoreCase("00:00:01")) {
                flag=true;
            }
        }
        assertEquals(true, flag);
    }

}
