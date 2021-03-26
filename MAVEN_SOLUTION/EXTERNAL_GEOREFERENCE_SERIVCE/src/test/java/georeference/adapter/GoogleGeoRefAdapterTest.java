/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package georeference.adapter;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.TravelMethod;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.json.JSONObject;
import georeference.ApiGeoreferenceService;

/**
 *
 * @author CarloS
 */
public class GoogleGeoRefAdapterTest {

    public GoogleGeoRefAdapterTest() {
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
     * Test of getDistanceAndDuration method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testGetDistanceAndDurationDriving() {
        System.out.println("getDistanceAndDuration");
        String from = "ISEP Porto";
        String to = "Casino Espinho";
        TravelMethod method = TravelMethod.DRIVING;
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO(27068, 1515);
        GeoRefServiceDTO result = instance.getDistanceAndDuration(from, to, method);
        assertEquals(expResult.distance, result.distance);
        assertEquals(expResult.duration, result.duration);
    }

    //@Test Este teste encontra-se em comentario porque depende do comboio que apanhar 
    public void testGetDistanceAndDurationTransit() {
        System.out.println("getDistanceAndDuration");
        String from = "ISEP Porto";
        String to = "Casino Espinho";
        TravelMethod method = TravelMethod.TRANSIT;
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO(22038, 3064);
        GeoRefServiceDTO result = instance.getDistanceAndDuration(from, to, method);
        assertEquals(expResult.distance, result.distance);
        assertEquals(expResult.duration, result.duration);
    }

    @Test
    public void testGetDistanceAndDurationBike() {
        System.out.println("getDistanceAndDuration");
        String from = "ISEP Porto";
        String to = "Casino Espinho";
        TravelMethod method = TravelMethod.BICYCLING;
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO(0, 0);
        GeoRefServiceDTO result = instance.getDistanceAndDuration(from, to, method);
        assertEquals(expResult.distance, result.distance);
        assertEquals(expResult.duration, result.duration);
    }

    /**
     * Test of distanceMatrixParser method, of class GoogleGeoRefAdapter.
     */
    //@Test
    public void testDistanceMatrixParser() {
        System.out.println("distanceMatrixParser");
        JSONObject result_2 = null;
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = null;
        GeoRefServiceDTO result = instance.distanceMatrixParser(result_2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFullLocation method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testGetFullLocation() {
        System.out.println("getFullLocation");
        String param = "R.+do+Gaiato+431,+4480-179+Azurara";
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO();
        expResult.country = "Portugal";
        expResult.district = "Porto";
        expResult.road = "Rua do Gaiato";
        expResult.post_code = "4480-179 Azurara";
        expResult.latitude = 41.33807849999999;
        expResult.longitude = -8.7403433;
        expResult.height = 0.0;
        expResult.distance = 0;
        expResult.duration = 0;
        GeoRefServiceDTO result = instance.getFullLocation(param);
        assertEquals(expResult.country, result.country);
        assertEquals(expResult.district, result.district);
        assertEquals(expResult.road, result.road);
        assertEquals(expResult.post_code, result.post_code);
        assertEquals(expResult.latitude, result.latitude,0.0015);
        assertEquals(expResult.longitude, result.longitude,0.0015);
    }

    /**
     * Test of addAddressToDTO method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testAddAddressToDTO() {
        System.out.println("addAddressToDTO");
        String s = "{   \"results\" : [      {         \"address_components\" : [            {               \"long_name\" : \"431\",               \"short_name\" : \"431\",               \"types\" : [ \"street_number\" ]            },            {               \"long_name\" : \"Rua Doutor AntÃ³nio Bernardino de Almeida\",               \"short_name\" : \"R. Dr. AntÃ³nio Bernardino de Almeida\",               \"types\" : [ \"route\" ]            },            {               \"long_name\" : \"Porto\",               \"short_name\" : \"Porto\",               \"types\" : [ \"locality\", \"political\" ]            },            {               \"long_name\" : \"Porto\",               \"short_name\" : \"Porto\",               \"types\" : [ \"administrative_area_level_1\", \"political\" ]            },            {               \"long_name\" : \"Portugal\",               \"short_name\" : \"PT\",               \"types\" : [ \"country\", \"political\" ]            },            {               \"long_name\" : \"4200-072\",               \"short_name\" : \"4200-072\",               \"types\" : [ \"postal_code\" ]            }         ],         \"formatted_address\" : \"R. Dr. AntÃ³nio Bernardino de Almeida 431, 4200-072 Porto, Portugal\",         \"geometry\" : {            \"bounds\" : {               \"northeast\" : {                  \"lat\" : 41.1789036,                  \"lng\" : -8.606379199999999               },               \"southwest\" : {                  \"lat\" : 41.1779652,                  \"lng\" : -8.607213699999999               }            },            \"location\" : {               \"lat\" : 41.178291,               \"lng\" : -8.607048899999999            },            \"location_type\" : \"ROOFTOP\",            \"viewport\" : {               \"northeast\" : {                  \"lat\" : 41.1797833802915,                  \"lng\" : -8.605447469708496               },               \"southwest\" : {                  \"lat\" : 41.1770854197085,                  \"lng\" : -8.608145430291501               }            }         },         \"place_id\" : \"ChIJy4V6OEhkJA0RUQLMF5vQClc\",         \"types\" : [ \"premise\" ]      }   ],   \"status\" : \"OK\"}";
        JSONObject field = new JSONObject(s);
        JSONObject firstResult = field.getJSONArray("results").getJSONObject(0);
        Object component = firstResult.getJSONArray("address_components").get(0);
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        GoogleGeoRefAdapter.addAddressToDTO((JSONObject) component, dto);
    }

    /**
     * Test of geocodeParser method, of class GoogleGeoRefAdapter.
     */
    //@Test
    public void testGeocodeParser() {
        System.out.println("geocodeParser");
        JSONObject body = null;
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = null;
        GeoRefServiceDTO result = instance.geocodeParser(body);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSurroundings method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testGetSurroundingsByCoordinates() {
        System.out.println("testGetSurroundingsByCoordinates");
        String center = "39.9,-7.8";
        String find = "agua";
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        dto.name = "Rio Ocreza, Rio Ocreza, Portugal";
        dto.latitude = 39.79208;
        dto.longitude = -7.633019500000001;
        dto.district = "Castelo Branco";
        expResult.add(dto);

        dto = new GeoRefServiceDTO();
        dto.name = "Praia Fluvial Couca Cerejeira, Praia Fluvial Couca Cerejeira, Portugal";
        dto.latitude = 39.809715;
        dto.longitude = -7.753256799999999;
        dto.district = "Castelo Branco";
        expResult.add(dto);
        Set<GeoRefServiceDTO> result = instance.getSurroundings(center, find);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSurroundings method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testGetSurroundingsByAddress() {
        System.out.println("testGetSurroundingsByAddress");
        String center = "Carrer Ponent, 82-80 Tarragona";
        //escolher um tipo não especificado no documento
        String find = "nuclear power";
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        dto.name = "Areva Np Services Spain Sl, Poligono Industrial de Constantí";
        dto.latitude = 41.1614627;
        dto.longitude = 1.177935;
        dto.district = "Catalunya";
        expResult.add(dto);
        Set<GeoRefServiceDTO> result = instance.getSurroundings(center, find);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        String address = "ISEP Porto";
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        String expResult = "41.1787497,-8.6077342";
        String result = instance.getCoordinates(address);
        assertEquals(expResult, result);
        address = "12.3456789,-100.987654321";
        expResult = address;
        result = instance.getCoordinates(address);
        assertEquals(expResult, result);
    }

    /**
     * Test of surroundingsParser method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testSurroundingsParser() {
        System.out.println("surroundingsParser");
        String param = "{\n"
                + "  \"next_page_token\": \"CqQCHAEAALT9ttNJaIOH1GhlcdT5e_AZoSmoomMZSxTpau2JBMOwRmNRB2RfJ6oRq53ohbqEiw7uThEKd63hw-FitRzlXgcYduL-9LGjObYLJR00aWExdCB_pYWLdrrhoyxTfYTJsCBWO-UaD_6nRcdlmxix4ZtSktKZEHF21MtZ2C-Gh231ixXaHNSNk7RXYPy5aUgPvZBVnFfgrmND1an1RsVypWNfhh8lbUj4dpNZpEidIuC7o6O2BO9Gvz-i4Khc1u-mvOhUqfaBUI1in_wwfNrJmyIy2fXC-BP63r9o20uOLsXXyiiAVqpYrUnfKQ1T-h0gVtmxEXt6_kjjXuNaaXjGQlQzZivYh7O1dP32rp_ziKrQ7drzAN1K9X3VtIsl7DtDNRIQvklxpFLq6KO6InKfFyt-_BoUJB7rR_1S_smD3IDKwnp88KD5dp4\",\n"
                + "  \"html_attributions\": [],\n"
                + "  \"results\": [\n"
                + "    {\n"
                + "      \"name\": \"PSP - Esquadra do Bom pastor\",\n"
                + "      \"geometry\": {\n"
                + "        \"location\": {\n"
                + "          \"lng\": -8.612932299999999,\n"
                + "          \"lat\": 41.169385\n"
                + "        }\n"
                + "      },\n"
                + "      \"vicinity\": \"Rua de Vale Formoso 488, Porto\",\n"
                + "    },\n"
                + "    {\n"
                + "      \"name\": \"PSP - Esquadra da Maia\",\n"
                + "      \"geometry\": {\n"
                + "        \"location\": {\n"
                + "          \"lng\": -8.6223221,\n"
                + "          \"lat\": 41.23109839999999\n"
                + "        }\n"
                + "      },\n"
                + "      \"vicinity\": \"Rua Doutor Augusto Martins 101\",\n"
                + "    }],\n"
                + "  \"status\": \"OK\"\n"
                + "}";
        JSONObject body = new JSONObject(param);
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        Set<GeoRefServiceDTO> expResult = new HashSet<>();
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        dto.name = "PSP - Esquadra do Bom pastor, Rua de Vale Formoso 488, Porto";
        dto.latitude = 41.169385;
        dto.longitude = -8.612932299999999;
        dto.district = "Porto";
        expResult.add(dto);

        dto = new GeoRefServiceDTO();
        dto.name = "PSP - Esquadra da Maia, Rua Doutor Augusto Martins 101";
        dto.latitude = 41.23109839999999;
        dto.longitude = -8.6223221;
        dto.district = "Porto";
        expResult.add(dto);

        Set<GeoRefServiceDTO> result = instance.surroundingsParser(body);

        assertEquals(expResult, result);
    }

    /**
     * Test of surroundingsParser method, of class GoogleGeoRefAdapter.
     */
    @Test(expected = JSONException.class)
    public void testSurroundingsParserInvalid() {
        System.out.println("surroundingsParser");
        String param = "{\n"
                + "  \"next_page_token\": \"CqQCHAEAALT9ttNJaIOH1GhlcdT5e_AZoSmoomMZSxTpau2JBMOwRmNRB2RfJ6oRq53ohbqEiw7uThEKd63hw-FitRzlXgcYduL-9LGjObYLJR00aWExdCB_pYWLdrrhoyxTfYTJsCBWO-UaD_6nRcdlmxix4ZtSktKZEHF21MtZ2C-Gh231ixXaHNSNk7RXYPy5aUgPvZBVnFfgrmND1an1RsVypWNfhh8lbUj4dpNZpEidIuC7o6O2BO9Gvz-i4Khc1u-mvOhUqfaBUI1in_wwfNrJmyIy2fXC-BP63r9o20uOLsXXyiiAVqpYrUnfKQ1T-h0gVtmxEXt6_kjjXuNaaXjGQlQzZivYh7O1dP32rp_ziKrQ7drzAN1K9X3VtIsl7DtDNRIQvklxpFLq6KO6InKfFyt-_BoUJB7rR_1S_smD3IDKwnp88KD5dp4\",\n"
                + "  \"html_attributions\": [],\n"
                + "  \"results\": [\n"
                + "    {\n"
                + "";
        JSONObject body = new JSONObject(param);
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        instance.surroundingsParser(body);
    }

    /**
     * Test of surroundingParser method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testSurroundingParser() {
        System.out.println("surroundingParser");
        String param = "{\n"
                + "  \"name\": \"Esuadras PSP de Custoias\",\n"
                + "  \"geometry\": {\n"
                + "    \"location\": {\n"
                + "      \"lng\": -8.644262099999999,\n"
                + "      \"lat\": 41.1997179\n"
                + "    }\n"
                + "  },\n"
                + "  \"vicinity\": \"Rua Nova de SÃ£o Gens 1267, CustÃ³ias\",\n"
                + "}";
        JSONObject surrounding = new JSONObject(param);
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO();
        expResult.name = "Esuadras PSP de Custoias, Rua Nova de SÃ£o Gens 1267, CustÃ³ias";
        expResult.latitude = 41.1997179;
        expResult.longitude = -8.644262099999999;
        expResult.district = "Porto";

        GeoRefServiceDTO result = instance.surroundingParser(surrounding);
        assertEquals(expResult.name, result.name);
        assertEquals(expResult.latitude, result.latitude,0.0015);
        assertEquals(expResult.longitude, result.longitude,0.0015);
        assertEquals(expResult.district, result.district);
    }

    /**
     * Test of surroundingParser method, of class GoogleGeoRefAdapter.
     */
    @Test(expected = JSONException.class)
    public void testSurroundingParserInvalid() {
        System.out.println("surroundingParser");
        String param = "{\n"
                + "  \"name\": \"Esuadras PSP de Custoias\",\n"
                + "  \"geometry\": {\n"
                + "    \"location\": {\n"
                + "      \"lng\": -8.644262099999999,\n"
                + "      \"lt\": 41.1997179\n"
                + "    }\n"
                + "  },\n"
                + "  \"vicinity\": \"Rua Nova de SÃ£o Gens 1267, CustÃ³ias\",\n"
                + "}";
        JSONObject surrounding = new JSONObject(param);
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        instance.surroundingParser(surrounding);
    }

    /**
     * Test of getElevationFromLocation method, of class GoogleGeoRefAdapter.
     */
    @Test
    public void testGetElevationFromLocation() {
        System.out.println("getElevationFromLocation");
        String param = "address=Rua+Doutor+Antonio+Bernardino+de+Almeida+431+Porto";
        Double b = 106.1256256103516;
        GoogleGeoRefAdapter instance = new GoogleGeoRefAdapter();
        GeoRefServiceDTO expResult = new GeoRefServiceDTO();
        expResult.latitude =41.178291;
        expResult.longitude = -8.607048899999999;
        expResult.height = b;
        GeoRefServiceDTO result = instance.getElevation(param);
        assertEquals(expResult.latitude, result.latitude,0.0015);
        assertEquals(expResult.longitude, result.longitude,0.0015);
        assertEquals(expResult.height, result.height,0.01F);
    }

}
