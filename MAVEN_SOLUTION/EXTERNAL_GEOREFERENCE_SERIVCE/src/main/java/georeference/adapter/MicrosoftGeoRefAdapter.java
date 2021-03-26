/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package georeference.adapter;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import georeference.applicationSetup.AppSettingsGEO;
import georeference.applicationSetup.ApplicationGEO;
import com.mycompany.georeference_interface.TravelMethod;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import georeference.ApiGeoreferenceService;

/**
 *
 * @author CarloS
 */
public class MicrosoftGeoRefAdapter extends ApiGeoreferenceService {

    private static final Properties SURROUNDINGS = new Properties();

    private static final String GEOCODE = "https://dev.virtualearth.net/REST/v1/Locations/";
    private static final String ELEVATION = "http://dev.virtualearth.net/REST/v1/Elevation/List?";
    private static final String DISTANCE_MATRIX = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?";
    private static final String SPACIAL = "https://spatial.virtualearth.net/REST/v1/data/c2ae584bb"
            + "ccc4916a0acf75d1e6947b4/NavteqEU/NavteqPOIs?$format=json&$top=200&$select=Latitude,Longitude,"
            + "DisplayName,PostalCode,AdminDistrict&";

    private static final String GEOCODE_KEY_PATH = "microsoft.geocode";
    private static final String ELEVATION_KEY_PATH = "microsoft.elevation";
    private static final String SPACIAL_KEY_PATH = "microsoft.spacial";
    private static final String DISTANCE_MATRIX_KEY_PATH = "microsoft.distancematrix";

    private final String geocodeKey;
    private final String elevationKey;
    private final String spacialKey;
    private final String distanceKey;

    public MicrosoftGeoRefAdapter() {
        this.geocodeKey = ApplicationGEO.settings().getProperty(GEOCODE_KEY_PATH);
        this.elevationKey = ApplicationGEO.settings().getProperty(ELEVATION_KEY_PATH);
        this.spacialKey = ApplicationGEO.settings().getProperty(SPACIAL_KEY_PATH);
        this.distanceKey = ApplicationGEO.settings().getProperty(DISTANCE_MATRIX_KEY_PATH);
    }

    static {
        try (InputStream propertiesStream = MicrosoftGeoRefAdapter.class.getClassLoader()
                .getResourceAsStream("microsoftSurrounding.properties")) {
            if (propertiesStream != null) {
                SURROUNDINGS.load(propertiesStream);
            } else {
                throw new FileNotFoundException(
                        "property file 'googleSurrounding.properties' not found in the classpath");
            }

        } catch (final IOException exio) {
            Logger.getLogger(AppSettingsGEO.class.getName()).log(Level.SEVERE, null, exio);
        }
    }

    @Override
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod method) {
        if (method == null || method == TravelMethod.BICYCLING) {
            method = TravelMethod.DRIVING;
        }

        from = getCoordinates(from);
        to = getCoordinates(to);

        String url = DISTANCE_MATRIX + "timeUnit=second&origins=" + from
                + "&destinations=" + to
                + "&travelMode=" + method.name().toLowerCase() + "&key=" + distanceKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new GeoRefServiceDTO();
        }

        return distanceMatrixParser(response);
    }

    protected final String getCoordinates(String address) {
        if (!address.matches(ApiGeoreferenceService.COORD_EXP)) {
            GeoRefServiceDTO temp = getFullLocation(address);
            address = temp.latitude + "," + temp.longitude;
        }
        return address;
    }

    protected final GeoRefServiceDTO distanceMatrixParser(JSONObject body) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        JSONObject firstResult = body.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0);
        JSONObject results = firstResult.getJSONArray("results").getJSONObject(0);

        dto.distance = (int) (results.getDouble("travelDistance") * 1000);
        dto.duration = (int) Math.round(results.getDouble("travelDuration"));
        return dto;
    }

    @Override
    public GeoRefServiceDTO getFullLocation(String param) {
        return getFullLocation(param.replaceAll("\\s+", "\\+"), false);
    }

    private GeoRefServiceDTO getFullLocation(String param, boolean b) {
        String url = GEOCODE
                + param + "?key=" + geocodeKey;
        JSONObject response;
        System.out.println(url);
        if ((response = getResponse(url)) == null) {
            return new GeoRefServiceDTO();
        }

        return geocodeParser(response, b);
    }

    protected final static void addAddressToDTO(JSONObject fullAddress, GeoRefServiceDTO dto) {

        dto.district = (fullAddress.getString("adminDistrict"));
        dto.country = (fullAddress.getString("countryRegion"));

        String formatted[] = fullAddress.get("addressLine").toString().split("\\s+");
        if (formatted.length > 1) {
            try {
                int n = Integer.parseInt(formatted[formatted.length - 1]);
                dto.number =n;
                dto.road = "";
                for (int i = 0; i < formatted.length - 1; i++) {
                    dto.road += formatted[i] + " ";
                }
                dto.road = dto.road.trim();
            } catch (NumberFormatException ex) {
                int j = 0;
                try {
                    int n = Integer.parseInt(formatted[0]);
                    dto.number =n;
                    j++;
                } catch (NumberFormatException nb) {
                }
                dto.road = "";
                for (int i = j; i < formatted.length; i++) {
                    dto.road += formatted[i] + " ";
                }
                dto.road = dto.road.trim();
            }
        }
        dto.post_code = (fullAddress.getString("postalCode"));
        dto.post_code += " " + (fullAddress.getString("locality"));

    }

    protected final GeoRefServiceDTO geocodeParser(JSONObject body, boolean isRecursiveCall) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        try {
            JSONObject firstResult = body.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0);

            JSONArray location = firstResult.getJSONObject("point").getJSONArray("coordinates");
            dto.latitude = (location.getDouble(0));
            dto.longitude = (location.getDouble(1));
            JSONObject address = firstResult.getJSONObject("address");
            addAddressToDTO(address, dto);

            return dto;
        } catch (org.json.JSONException ex) {
            if (!isRecursiveCall) {
                return getFullLocation(dto.latitude + "," + dto.longitude, true);
            }

            return dto;
        }
    }

    @Override
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find) {
        find = find.trim().replaceAll("\\s+", "\\+");
        String semiQuery = SURROUNDINGS.getProperty("microsoft." + find);
        if (semiQuery != null) {
            find = "$filter=EntityTypeID+eq+" + semiQuery;
        } else {
            find = "$filter=DisplayName+gt+'" + find + "'+and+DisplayName+lt+'" + find + "z'";
        }

        if (!center.matches(ApiGeoreferenceService.COORD_EXP)) {
            center = "'" + center + "'";
        }
        String url = SPACIAL + "spatialFilter=nearby(" + center.replaceAll("\\s+", "\\+") + "," + ApiGeoreferenceService.SEARCH_RADIUS_KM
                + ")&" + find
                + "&key=" + spacialKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new HashSet<>();
        }

        return surroundingsParser(response);
    }

    protected final Set<GeoRefServiceDTO> surroundingsParser(JSONObject body) {
        Set<GeoRefServiceDTO> found = new HashSet<>();
        try {
            JSONArray resultList = body.getJSONObject("d").getJSONArray("results");
            for (int i = 0; i < resultList.length(); i++) {
                found.add(surroundingParser(resultList.getJSONObject(i)));
            }

            return found;
        } catch (org.json.JSONException ex) {
            return found;
        }
    }

    protected final GeoRefServiceDTO surroundingParser(JSONObject surrounding) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        dto.name = surrounding.getString("DisplayName") + ", " + surrounding.getString("PostalCode");
        dto.latitude = surrounding.getDouble("Latitude");
        dto.longitude = surrounding.getDouble("Longitude");
        dto.district = surrounding.getString("AdminDistrict");

        return dto;
    }

    @Override
    public GeoRefServiceDTO getElevation(String param) {
        param = getCoordinates(param);

        String url = ELEVATION + "points="
                + param + "&key=" + elevationKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new GeoRefServiceDTO();
        }
        GeoRefServiceDTO dto=new GeoRefServiceDTO();
        String temp[]=param.split(",");
        dto.latitude=Double.parseDouble(temp[0].trim());
        dto.longitude=Double.parseDouble(temp[1].trim());
        return dto.merge(elevationParser(response));
    }

    protected final GeoRefServiceDTO elevationParser(JSONObject body) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        try {
            JSONObject firstResult = body.getJSONArray("resourceSets").getJSONObject(0);

            JSONArray geocodePoints1 = (JSONArray) firstResult.get("resources");
            JSONObject g1 = (JSONObject) geocodePoints1.get(0);
            JSONArray coordinates1 = (JSONArray) g1.get("elevations");
            int height = (Integer) coordinates1.get(0);
            dto.height = (double) height;

            return dto;
        } catch (org.json.JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
