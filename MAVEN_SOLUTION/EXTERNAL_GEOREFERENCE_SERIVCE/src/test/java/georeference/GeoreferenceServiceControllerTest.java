/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package georeference;

import com.mycompany.georeference_interface.TravelMethod;
import com.mycompany.georeference_interface.GeoRefServiceDTO;
import georeference.adapter.GoogleGeoRefAdapter;
import georeference.adapter.MicrosoftGeoRefAdapter;
import georeference.applicationSetup.AppSettingsGEO;
import georeference.applicationSetup.ApplicationGEO;
import java.util.HashSet;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class GeoreferenceServiceControllerTest {

    static String selected[];
    static final String google = "georeference.service.api.google";
    static final String microsoft = "georeference.service.api.microsoft";

    public GeoreferenceServiceControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        AppSettingsGEO set = ApplicationGEO.settings();
        selected = set.getProperty(AppSettingsGEO.SELECTED_API).split("\\|");
    }

    /**
     * Test of getDistanceAndDuration method, of class
     * GeoreferenceServiceController.
     */
    @Test
    public void testGetDistanceAndDuration() {
        System.out.println("getDistanceAndDuration");
        GeoRefServiceDTO result;
        GeoRefServiceDTO expected = new GeoRefServiceDTO();
        String from = "Casino Espinho";
        String to = "ISEP Porto";
        TravelMethod method = TravelMethod.DRIVING;
        if (selected.length == 2) {
            System.out.println("getDistanceAndDuration - both");
            expected.merge(new GoogleGeoRefAdapter().getDistanceAndDuration(from, to, method));
            expected.merge(new MicrosoftGeoRefAdapter().getDistanceAndDuration(from, to, method));
        } else {
            if (selected.length == 0) {
                fail("No configured Implementations");
            }
            System.out.println("getDistanceAndDuration -" + selected[0]);
            if (selected[0].equals(google)) {
                expected.merge(new GoogleGeoRefAdapter().getDistanceAndDuration(from, to, method));
            } else if (selected[0].equals(microsoft)) {
                expected.merge(new MicrosoftGeoRefAdapter().getDistanceAndDuration(from, to, method));
            }
        }
        result = new GeoreferenceServiceController().getDistanceAndDuration(from, to, method);
        assertEquals(expected.distance, result.distance, 0.0015);
        assertEquals(expected.duration, result.duration);
    }

    /**
     * Test of getFullLocation method, of class GeoreferenceServiceController.
     */
    @Test
    public void testGetFullLocation() {
        System.out.println("testGetFullLocation");
        GeoRefServiceDTO result;
        GeoRefServiceDTO expected = new GeoRefServiceDTO();
        String from = "R. do Património S/N, Maia";
        if (selected.length == 2) {
            System.out.println("testGetFullLocation - both");
            expected.merge(new GoogleGeoRefAdapter().getFullLocation(from));
            expected.merge(new MicrosoftGeoRefAdapter().getFullLocation(from));
        } else {
            if (selected.length == 0) {
                fail("No configured Implementations");
            }
            System.out.println("testGetFullLocation -" + selected[0]);
            if (selected[0].equals(google)) {
                expected.merge(new GoogleGeoRefAdapter().getFullLocation(from));
            } else if (selected[0].equals(microsoft)) {
                expected.merge(new MicrosoftGeoRefAdapter().getFullLocation(from));
            }
        }
        result = new GeoreferenceServiceController().getFullLocation(from);
        assertEquals(expected.distance, result.distance, 0.0015);
        assertEquals(expected.longitude, result.longitude, 0.0015);
        assertEquals(expected.latitude, result.latitude, 0.0015);        
        assertEquals(expected.post_code, result.post_code);
        assertEquals(expected.district, result.district);
        assertEquals(expected.number, result.number);
        assertEquals(expected.road, result.road);
        assertEquals(expected.country, result.country);
        assertEquals(expected.duration, result.duration);
    }

    /**
     * Test of getSurroundings method, of class GeoreferenceServiceController.
     */
    @Test
    public void testGetSurroundings() {
        System.out.println("testGetElevation");
        Set<GeoRefServiceDTO> result;
        Set<GeoRefServiceDTO> expected = new HashSet();
        String from = "ISEP Porto";
        String type = "hospital";
        if (selected.length == 2) {
            System.out.println("testGetElevation - both");
            expected.addAll(new GoogleGeoRefAdapter().getSurroundings(from,type));
            expected.addAll(new MicrosoftGeoRefAdapter().getSurroundings(from,type));
        } else {
            if (selected.length == 0) {
                fail("No configured Implementations");
            }
            System.out.println("testGetElevation -" + selected[0]);
            if (selected[0].equals(google)) {
                expected.addAll(new GoogleGeoRefAdapter().getSurroundings(from,type));
            } else if (selected[0].equals(microsoft)) {
                expected.addAll(new MicrosoftGeoRefAdapter().getSurroundings(from,type));
            }
        }
        result = new GeoreferenceServiceController().getSurroundings(from,type);
        assertEquals(result.size(),expected.size());
        assertTrue(result.containsAll(expected));
    }

    /**
     * Test of getElevation method, of class GeoreferenceServiceController.
     */
    @Test
    public void testGetElevation() {
         System.out.println("testGetElevation");
        GeoRefServiceDTO result;
        GeoRefServiceDTO expected = new GeoRefServiceDTO();
        String from = "ISEP Porto";
        if (selected.length == 2) {
            System.out.println("testGetElevation - both");
            expected.merge(new GoogleGeoRefAdapter().getElevation(from));
            expected.merge(new MicrosoftGeoRefAdapter().getElevation(from));
        } else {
            if (selected.length == 0) {
                fail("No configured Implementations");
            }
            System.out.println("testGetElevation -" + selected[0]);
            if (selected[0].equals(google)) {
                expected.merge(new GoogleGeoRefAdapter().getElevation(from));
            } else if (selected[0].equals(microsoft)) {
                expected.merge(new MicrosoftGeoRefAdapter().getElevation(from));
            }
        }
        result = new GeoreferenceServiceController().getElevation(from);
        assertEquals(expected.height, result.height, 0.0015);
    }
    
    /**
     * Test of getElevationDifferenceBetweenTwoLocations method, of class ExternalGeoRefServiceController.
     */
    @Test
    public void testGetElevationDifferenceBetweenTwoLocations() {
        System.out.println("getElevationDifferenceBetweenTwoLocations");
        String loc1 = "431,Rua Doutor Antonio Bernardino de Almeida,Porto";
        String loc2 = "115,Rua Sara Afonso,Porto";
        GeoreferenceServiceController instance = new GeoreferenceServiceController();
        double expResult = -1;
        if (selected.length == 2) {
            System.out.println("testGetElevationDifferenceBetweenTwoLocations - both");
            expResult = 28.699367523193388;
        } else {
            if (selected.length == 0) {
                fail("No configured Implementations");
            }
            System.out.println("testGetElevationDifferenceBetweenTwoLocations -" + selected[0]);
            if (selected[0].equals(google)) {
                expResult = 30.39873504638676;
            } else if (selected[0].equals(microsoft)) {
                expResult = 27.0;
            }
        }
        double result = instance.getElevationDifferenceBetweenTwoLocations(loc1,loc2);
        assertEquals(expResult, result, 0.0015);
    }
    
    /**
     * Test of openStaticImage method, of class GeoreferenceServiceController.
     */
    @Test
    public void testOpenStaticImage() {
        System.out.println("openStaticImage");
        String address = "Isep Porto";
        GeoreferenceServiceController instance = new GeoreferenceServiceController();
        boolean expResult = true;
        boolean result = instance.openStaticImage(address);
        assertEquals(expResult, result); 
    }

}
