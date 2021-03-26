/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

import core.domain.location.GPSLocationBuilder;
import core.domain.location.GPSLocation;
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
public class GPSLocationBuilderTest {

    public GPSLocationBuilderTest() {
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
     * Test of withAltitude method, of class GPSLocationBuilder.
     */
    @Test
    public void testWithAltitude() {
        System.out.println("withAltitude");
        float height = 30.0F;
        GPSLocationBuilder instance = new GPSLocationBuilder(10.0F, 10.0F);
        GPSLocationBuilder expResult = new GPSLocationBuilder(10.0F, 10.0F);;
        GPSLocationBuilder ep = expResult.withAltitude(height);
        GPSLocationBuilder result = instance.withAltitude(height);
        assertEquals(ep.height, result.height, 0.0);
    }

    /**
     * Test of build method, of class GPSLocationBuilder.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        GPSLocationBuilder instance = new GPSLocationBuilder(10.0F, 10.0F);
        GPSLocationBuilder expResult = new GPSLocationBuilder(10.0F, 10.0F);
        GPSLocation result = instance.build();
        assertEquals(expResult.height, result.getAltitude(),0.0F);
        assertEquals(expResult.latitude, result.getLatitude(),0.0F);
        assertEquals(expResult.longitude, result.getLongitude(),0.0F);
    }

}
