/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.utils;

import core.domain.location.GPSLocation;
import core.domain.Surrounding.Surrounding;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joaoflores
 */
public class RegisterSurroundingByLocationTest {

    public RegisterSurroundingByLocationTest() {
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
     * Test of obtainSurroundingByLocation method, of class
     * RegisterSurroundingByLocation.
     * @throws java.lang.Exception
     */
    //@Test
    public void testObtainSurroundingByLocation() throws Exception {
        System.out.println("obtainSurroundingByLocation");
        
        String category = "Hospital";
        String stName = "Hostital Sao Joao";
        String address = "Alameda Prof. Hernâni Monteiro,4200-319 Porto";

        GPSLocation gps = new GPSLocation(41.183052F, -8.6009655F, 115.754036F, "Porto");
        RegisterSurroundingByLocation instance = new RegisterSurroundingByLocation();
        Surrounding result = instance.obtainSurroundingByLocation(category, stName, address);
        
        assertEquals(stName, result.obtainSName().toString());
        assertEquals(category, result.obtainSTName().toString());        
        assertEquals(gps.getLatitude(), result.obtainLocation().getLatitude(),0.01);
        assertEquals(gps.getLongitude(), result.obtainLocation().getLongitude(),0.01);
        assertEquals(gps.getAltitude(), result.obtainLocation().getAltitude(),0.01);
        assertEquals(gps.getAltitude(), result.obtainLocation().getAltitude(),0.01);
    }
 
}
