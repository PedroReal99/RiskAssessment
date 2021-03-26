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
import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import georeference.ApiGeoreferenceService;

/**
 *
 * @author CarloS
 */
public class GoogleGeoRefAdapter extends ApiGeoreferenceService {

    private static final Properties SURROUNDINGS = new Properties();

    private static final String GEOCODE = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static final String ELEVATION = "https://maps.googleapis.com/maps/api/elevation/json?";
    private static final String NEARBY_PLACES = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String DISTANCE_MATRIX = "https://maps.googleapis.com/maps/api/distancematrix/json?";
    private static final String STREET_VIEW = "https://maps.googleapis.com/maps/api/staticmap?";

    private static final String GEOCODE_KEY_PATH = "google.geocode";
    private static final String ELEVATION_KEY_PATH = "google.elevation";
    private static final String PLACES_KEY_PATH = "google.places";
    private static final String DISTANCE_MATRIX_KEY_PATH = "google.distancematrix";
    private static final String STREET_VIEW_PATH = "google.streetview";

    private final String geocodeKey;
    private final String elevationKey;
    private final String placesKey;
    private final String distanceKey;
    private final String streetviewKey;

    static {
        try (InputStream propertiesStream = GoogleGeoRefAdapter.class.getClassLoader()
                .getResourceAsStream("googleSurrounding.properties")) {
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

    public GoogleGeoRefAdapter() {
        this.geocodeKey = ApplicationGEO.settings().getProperty(GEOCODE_KEY_PATH);
        this.elevationKey = ApplicationGEO.settings().getProperty(ELEVATION_KEY_PATH);
        this.placesKey = ApplicationGEO.settings().getProperty(PLACES_KEY_PATH);
        this.distanceKey = ApplicationGEO.settings().getProperty(DISTANCE_MATRIX_KEY_PATH);
        this.streetviewKey = ApplicationGEO.settings().getProperty(STREET_VIEW_PATH);
    }

    @Override
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod method) {
        if (method == null) {
            method = TravelMethod.DRIVING;
        }
        String url = DISTANCE_MATRIX + "units=metric&origins=" + from.replaceAll("\\s+", "\\+")
                + "&destinations=" + to.replaceAll("\\s+", "\\+")
                + "&mode=" + method.name().toLowerCase() + "&key=" + distanceKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new GeoRefServiceDTO();
        }

        return distanceMatrixParser(response);
    }

    protected final GeoRefServiceDTO distanceMatrixParser(JSONObject result) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        JSONObject rows = result.getJSONArray("rows").getJSONObject(0);
        JSONObject elements = rows.getJSONArray("elements").getJSONObject(0);

        if ("ZERO_RESULTS".equals(elements.get("status"))) {
            return dto;
        }

        JSONObject distance = elements.getJSONObject("distance");
        JSONObject duration = elements.getJSONObject("duration");

        dto.distance = (int) distance.get("value");
        dto.duration = (int) duration.get("value");
        return dto;
    }

    @Override
    public GeoRefServiceDTO getFullLocation(String param) {
        String url = GEOCODE + "address="
                + param.replaceAll("\\s+", "\\+") + "&key=" + geocodeKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new GeoRefServiceDTO();
        }

        return geocodeParser(response);
    }

    protected final static void addAddressToDTO(JSONObject field, GeoRefServiceDTO dto) {
        JSONArray types = (JSONArray) field.get("types");
        String type = types.get(0).toString();
        switch (type) {
            case "street_number":
                try {
                    dto.number = (Integer.parseInt(field.getString("long_name")));
                } catch (NumberFormatException ex) {
                }
                break;
            case "route":
                dto.road = (field.getString("long_name"));
                break;
            case "administrative_area_level_1":
                dto.district = (field.getString("long_name").replaceFirst(" District", ""));
                break;
            case "country":
                dto.country = (field.getString("long_name"));
                break;
        }
    }

    protected final GeoRefServiceDTO geocodeParser(JSONObject body) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        try {
            JSONObject firstResult = body.getJSONArray("results").getJSONObject(0);
            for (Object component : firstResult.getJSONArray("address_components")) {
                addAddressToDTO((JSONObject) component, dto);
            }
            String formatted[] = firstResult.get("formatted_address").toString().split(",");
            if (formatted.length > 1) {
                dto.post_code = (formatted[formatted.length - 2].trim());
            }
            JSONObject location = firstResult.getJSONObject("geometry").getJSONObject("location");
            dto.latitude = (location.getDouble("lat"));
            dto.longitude = (location.getDouble("lng"));

            return dto;
        } catch (org.json.JSONException ex) {
            return null;
        }
    }

    @Override
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find) {
        String semiQuery = SURROUNDINGS.getProperty("google." + find);
        if (semiQuery != null) {
            find = semiQuery;
        }
        center = getCoordinates(center);

        String url = NEARBY_PLACES + "radius=" + ApiGeoreferenceService.SEARCH_RADIUS_M + "&location=" + center.replaceAll("\\s+", "\\+")
                + "&keyword=" + find.replaceAll("\\s+", "\\+")
                + "&key=" + placesKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new HashSet<>();
        }
        return surroundingsParser(response);
    }

    protected final String getCoordinates(String address) {
        if (!address.matches(ApiGeoreferenceService.COORD_EXP)) {
            GeoRefServiceDTO temp = getFullLocation(address);
            
            if(temp==null){
                return "0,0";
            }
            address = temp.latitude + "," + temp.longitude;
        }
        return address;
    }

    protected final Set<GeoRefServiceDTO> surroundingsParser(JSONObject body) {
        Set<GeoRefServiceDTO> found = new HashSet<>();
        try {
            JSONArray resultList = body.getJSONArray("results");
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
        dto.name = surrounding.getString("name") + ", " + surrounding.getString("vicinity");
        JSONObject location = surrounding.getJSONObject("geometry").getJSONObject("location");
        dto.latitude = (location.getDouble("lat"));
        dto.longitude = (location.getDouble("lng"));
        //garantir que a location tem distrito
        dto.district = getFullLocation(dto.latitude + "," + dto.longitude).district;
        return dto;
    }

    @Override
    public GeoRefServiceDTO getElevation(String param) {
        param = getCoordinates(param);

        String url = ELEVATION + "locations="
                + param + "&key=" + elevationKey;
        System.out.println(url);
        JSONObject response;
        if ((response = getResponse(url)) == null) {
            return new GeoRefServiceDTO();
        }

        return elevationParser(response);

    }

    protected final GeoRefServiceDTO elevationParser(JSONObject body) {
        GeoRefServiceDTO dto = new GeoRefServiceDTO();
        try {
            JSONObject firstResult = body.getJSONArray("results").getJSONObject(0);

            dto.height = firstResult.getDouble("elevation");
            JSONObject location = firstResult.getJSONObject("location");
            dto.latitude = (location.getDouble("lat"));
            dto.longitude = (location.getDouble("lng"));

            return dto;
        } catch (org.json.JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean openStaticImage(String local) {
        //String zoom = "&zoom=18";
        String size = "size=1024x800";
        String mapType = "&maptype=roadmap%20";
        String markers = "&markers=color:red%7Clabel:P%7C";
        String url = STREET_VIEW + size + mapType + markers + local.replaceAll("\\s+", "\\+") + "&key=" + streetviewKey;
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {

                //this is the only way that convert special characters into ascii to open the browser with the uri because with special caracters the method doesn't work
                URI parsedUri = new URI(new URI(url).toASCIIString());
                desktop.browse(parsedUri);

            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
