/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package georeference.adapter;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.TravelMethod;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CarloS
 */
public class MicrosoftGeoRefAdapterTest {

    public MicrosoftGeoRefAdapterTest() {
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
     * Test of getDistanceAndDuration method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetDistanceAndDuration() {
        System.out.println("getDistanceAndDuration");
        String from = "Vancouver,BC";
        String to = "San Francisco,CA";
        TravelMethod method = TravelMethod.BICYCLING;
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        int expMeters = 1527646;
        int expSeconds = 51042;
        GeoRefServiceDTO result = instance.getDistanceAndDuration(from, to, method);
        assertEquals(expMeters, result.distance);
        assertEquals(expSeconds, result.duration);
    }

    /**
     * Test of getCoordinates method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        String address = "Barragem de Alqueva, Évora";
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        String expResult = "38.7691801,-7.1936222";
        String result = instance.getCoordinates(address);
        assertEquals(expResult, result);
        address = "12.3456789,-100.987654321";
        expResult = address;
        result = instance.getCoordinates(address);
        assertEquals(expResult, result);
    }

    /**
     * Test of distanceMatrixParser method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testDistanceMatrixParser() {
        System.out.println("distanceMatrixParser");
        String s="{\"authenticationResultCode\":\"ValidCredentials\",\"brandLogoUri\":\"http:\\/\\/dev.virtualearth.net\\/Branding\\/logo_powered_by.png\",\"copyright\":\"Copyright © 2019 Microsoft and its suppliers. All rights reserved. This API cannot be accessed and the content and any results may not be used, reproduced or transmitted in any manner without express written permission from Microsoft Corporation.\",\"resourceSets\":[{\"estimatedTotal\":1,\"resources\":[{\"__type\":\"DistanceMatrix:http:\\/\\/schemas.microsoft.com\\/search\\/local\\/ws\\/rest\\/v1\",\"destinations\":[{\"latitude\":45.5347,\"longitude\":-122.6231}],\"errorMessage\":\"Request accepted.\",\"origins\":[{\"latitude\":47.6044,\"longitude\":-122.3345}],\"results\":[{\"destinationIndex\":0,\"originIndex\":0,\"totalWalkDuration\":0,\"travelDistance\":281.546,\"travelDuration\":165.723}]}]}],\"statusCode\":200,\"statusDescription\":\"OK\",\"traceId\":\"a8f38d8e0628443a8211bbbc6288f0a5|DU00000D7A|7.7.0.0\"}";
        JSONObject body = new JSONObject(s);
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
       GeoRefServiceDTO expResult = new GeoRefServiceDTO(281546,166);
        GeoRefServiceDTO result = instance.distanceMatrixParser(body);
        assertEquals(expResult.distance, result.distance);
        assertEquals(expResult.duration, result.duration);
       
    }

    /**
     * Test of getFullLocation method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetFullLocation() {
        System.out.println("getFullLocation");
        String param = "1600 Amphitheatre Parkway,Mountain View,CA";
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        String road = "Amphitheatre Pkwy";
        int number = 1600;
        String country = "United States";
        String district = "CA";
        String c = "Mountain View Santa Clara County";
        GeoRefServiceDTO result = instance.getFullLocation(param);
        assertEquals(number, result.number);
        assertEquals(road, result.road);
        assertEquals(district, result.district);
        assertEquals(country, result.country);
        assertEquals("94043 Mountain View", result.post_code);
    }

    /**
     * Test of addAddressToDTO method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testAddAddressToDTO() {
        System.out.println("addAddressToDTO");
        String road = "Amphitheatre Pkwy";
        int number = 1600;
        String country = "United States";
        String district = "CA";
        String c = "Mountain View Santa Clara County";
        JSONObject fullAddress = new JSONObject();
        fullAddress.put("addressLine", "1600 Amphitheatre Pkwy");
        fullAddress.put("adminDistrict", "CA");
        fullAddress.put("adminDistrict2", "Santa Clara County");
        fullAddress.put("countryRegion", "United States");
        fullAddress.put("formattedAddress", "1600 Amphitheatre Pkwy, Mountain View, CA 94043");
        fullAddress.put("locality", "Mountain View");
        fullAddress.put("postalCode", "94043");
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        MicrosoftGeoRefAdapter.addAddressToDTO(fullAddress, dto);
        assertEquals(number, dto.number);
        assertEquals(road, dto.road);
        assertEquals(district, dto.district);
        assertEquals(country, dto.country);
        assertEquals("94043 Mountain View", dto.post_code);
    }

    /**
     * Test of geocodeParser method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGeocodeParser() throws FileNotFoundException, IOException, ParseException {
        System.out.println("geocodeParser");
        String s = "{\"authenticationResultCode\":\"ValidCredentials\",\"brandLogoUri\":\"http:\\/\\/dev.virtualearth.net\\/Branding\\/logo_powered_by.png\",\"copyright\":\"Copyright © 2019 Microsoft and its suppliers. All rights reserved. This API cannot be accessed and the content and any results may not be used, reproduced or transmitted in any manner without express written permission from Microsoft Corporation.\",\"resourceSets\":[{\"estimatedTotal\":1,\"resources\":[{\"__type\":\"Location:http:\\/\\/schemas.microsoft.com\\/search\\/local\\/ws\\/rest\\/v1\",\"bbox\":[37.418299282429324,-122.0926827098349,37.426024717570677,-122.07971329016509],\"name\":\"1600 Amphitheatre Pkwy, Mountain View, CA 94043\",\"point\":{\"type\":\"Point\",\"coordinates\":[37.422162,-122.086198]},\"address\":{\"addressLine\":\"1600 Amphitheatre Pkwy\",\"adminDistrict\":\"CA\",\"adminDistrict2\":\"Santa Clara County\",\"countryRegion\":\"United States\",\"formattedAddress\":\"1600 Amphitheatre Pkwy, Mountain View, CA 94043\",\"locality\":\"Mountain View\",\"postalCode\":\"94043\"},\"confidence\":\"High\",\"entityType\":\"Address\",\"geocodePoints\":[{\"type\":\"Point\",\"coordinates\":[37.422162,-122.086198],\"calculationMethod\":\"Rooftop\",\"usageTypes\":[\"Display\"]},{\"type\":\"Point\",\"coordinates\":[37.4233025444043,-122.08587524574],\"calculationMethod\":\"Rooftop\",\"usageTypes\":[\"Route\"]}],\"matchCodes\":[\"Good\"]}]}],\"statusCode\":200,\"statusDescription\":\"OK\",\"traceId\":\"a2e014f44ffc44ad83c5a2ec1133caae|DU00000D63|7.7.0.0|Ref A: 4C132C701ACA4CCD8EBB0B980DE10ACF Ref B: DB3EDGE1022 Ref C: 2019-06-11T16:46:50Z\"}";
        JSONObject body = new JSONObject(s);
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        String road = "Amphitheatre Pkwy";
        int number = 1600;
        String country = "United States";
        String district = "CA";
        String c = "Mountain View Santa Clara County";

        GeoRefServiceDTO result = instance.geocodeParser(body, false);

        assertEquals(number, result.number);
        assertEquals(road, result.road);
        assertEquals(district, result.district);
        assertEquals(country, result.country);
        assertEquals("94043 Mountain View", result.post_code);

    }

    /**
     * Test of getSurroundings method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetSurroundingsByCoordinates() {
        System.out.println("testGetSurroundingsByCoordinates");
        String center = "38.28252,-7.42268";
        String find = "barragem";
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        dto.latitude = 38.36908;
        dto.longitude = -7.35336;
        dto.name = "Barragem de Alqueva, 7240";
        dto.district = "Évora";
        expResult.add(dto);

        dto = new GeoRefServiceDTO();
        dto.latitude = 38.19596;
        dto.longitude = -7.492;
        dto.name = "Barragem de Alqueva, 7860";
        dto.district = "Beja";
        expResult.add(dto);
        Set<GeoRefServiceDTO> result = instance.getSurroundings(center, find);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSurroundings method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetSurroundingsByAddress() {
        System.out.println("testGetSurroundingsByAddress");
        String center = "Regengos de Monsaraz, Evora";
        String find = "policia";
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        dto.latitude = 38.42663;
        dto.longitude = -7.53096;
        dto.name = "GNR de Regengos de Monsaraz, 7200-314";
        dto.district = "Évora";
        expResult.add(dto);

        dto = new GeoRefServiceDTO();
        dto.latitude = 38.45191;
        dto.longitude = -7.38031;
        dto.name = "GNR de Telheiro, 7200-181";
        dto.district = "Évora";
        expResult.add(dto);

        dto = new GeoRefServiceDTO();
        dto.latitude = 38.54489;
        dto.longitude = -7.48157;
        dto.name = "GNR de Santiago Maior, 7200-012";
        dto.district = "Évora";
        expResult.add(dto);
        Set<GeoRefServiceDTO> result = instance.getSurroundings(center, find);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSurroundings method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetSurroundingsNoResults() {
        System.out.println("testGetSurroundingsNoResults");
        String center = "Regengos de Monsaraz, Evora";
        String find = "barragem";
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();

        Set<GeoRefServiceDTO> result = instance.getSurroundings(center, find);
        assertEquals(expResult, result);
    }

    /**
     * Test of surroundingsParser method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testSurroundingsParser() {
        System.out.println("surroundingsParser");
        String param = "{\"d\": {\n"
                + "  \"__copyright\": \"© 2019 Microsoft and its suppliers.  This API and any results cannot be used or accessed without Microsoft's express written permission.\",\n"
                + "  \"results\": [\n"
                + "    {\n"
                + "      \"DisplayName\": \"PSP da Maia\",\n"
                + "      \"PostalCode\": \"4470-146\",\n"
                + "      \"AdminDistrict\": \"Porto\",\n"
                + "      \"Latitude\": 41.2312,\n"
                + "      \"Longitude\": -8.62198\n"
                + "    },\n"
                + "    {\n"
                + "      \"DisplayName\": \"PSP da Maia\",\n"
                + "      \"PostalCode\": \"4475-458\",\n"
                + "      \"AdminDistrict\": \"Porto\",\n"
                + "      \"Latitude\": 41.23515,\n"
                + "      \"Longitude\": -8.58881\n"
                + "    }]\n"
                + "}}";
        JSONObject body = new JSONObject(param);
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();

        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        dto.latitude =41.2312;
        dto.longitude = -8.62198;
        dto.name = "PSP da Maia, 4470-146";
        expResult.add(dto);

        dto = new GeoRefServiceDTO();
        dto.latitude = 41.23515;
        dto.longitude = -8.58881;
        dto.name = "PSP da Maia, 4475-458";
        expResult.add(dto);
        Set<GeoRefServiceDTO> result = instance.surroundingsParser(body);
        assertEquals(expResult, result);
    }

    /**
     * Test of surroundingsParser method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testSurroundingsParserInvalid() {
        System.out.println("surroundingsParser");
        String param = "{\"d\": {\n"
                + "  \"__copyright\": \"© 2019 Microsoft and its suppliers.  This API and any results cannot be used or accessed without Microsoft's express written permission.\",\n"
                + "  \"resuls\": [\n"
                + "    {\n"
                + "      \"DisplayName\": \"PSP da Maia\",\n"
                + "      \"PostalCode\": \"4470-146\",\n"
                + "      \"AdminDistrict\": \"Porto\",\n"
                + "      \"Latitude\": 41.2312,\n"
                + "      \"Longitude\": -8.62198\n"
                + "    },\n"
                + "    {\n"
                + "      \"DisplayName\": \"PSP da Maia\",\n"
                + "      \"PostalCode\": \"4475-458\",\n"
                + "      \"AdminDistrict\": \"Porto\",\n"
                + "      \"Latitude\": 41.23515,\n"
                + "      \"Longitude\": -8.58881\n"
                + "    }]\n"
                + "}}";
        JSONObject body = new JSONObject(param);
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();
        Set<GeoRefServiceDTO> result = instance.surroundingsParser(body);
        assertEquals(expResult, result);
    }

    /**
     * Test of surroundingParser method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testSurroundingParser() {
        System.out.println("surroundingParser");
        String param = "{\n"
                + "  \"DisplayName\": \"GNR Rua do Carmo\","
                + "  \"PostalCode\": \"4050-164\","
                + "  \"AdminDistrict\": \"Porto\","
                + "  \"Latitude\": 41.14727,"
                + "  \"Longitude\": -8.61716"
                + "}";
        JSONObject surrounding = new JSONObject(param);
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO();
        expResult.latitude = 41.14727;
        expResult.longitude = -8.61716;
        expResult.name = "GNR Rua do Carmo, 4050-164";
        expResult.district = "Porto";
        GeoRefServiceDTO result = instance.surroundingParser(surrounding);
        assertEquals(expResult.name, result.name);
        assertEquals(expResult.latitude, result.latitude,0.0015);
        assertEquals(expResult.longitude, result.longitude,0.0015);
        assertEquals(expResult.district, result.district);
    }

    /**
     * Test of surroundingParser method, of class MicrosoftGeoRefAdapter.
     */
    @Test(expected = JSONException.class)
    public void testSurroundingParserInvalid() {
        System.out.println("surroundingParser");
        String param = "{\n"
                + "  \"DisplayName\": \"GNR Rua do Carmo\","
                + "  \"PostalCode\": \"4050-164\","
                + "  \"AdminDistrict\": \"Porto\","
                + "  \"Latitude\": 41.14727,"
                + "}";
        JSONObject surrounding = new JSONObject(param);
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        instance.surroundingParser(surrounding);

    }

    /**
     * Test of getElevationFromLocation method, of class MicrosoftGeoRefAdapter.
     */
    @Test
    public void testGetElevationFromLocation() {
        System.out.println("getElevationFromLocation");
        String param = "address=Rua+Doutor+Antonio+Bernardino+de+Almeida+431+Porto";
        Double elevation= 109.0;
        MicrosoftGeoRefAdapter instance = new MicrosoftGeoRefAdapter();
        GeoRefServiceDTO result = instance.getElevation(param);       
        assertEquals(elevation, result.height,0.01F);
    }

}
