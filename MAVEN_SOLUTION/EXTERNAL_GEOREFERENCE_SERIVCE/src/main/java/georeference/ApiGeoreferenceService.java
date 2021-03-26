/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package georeference;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.GeoreferenceService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author CarloS
 */
public abstract class ApiGeoreferenceService implements GeoreferenceService{

    protected static final String COORD_EXP = "\\-?(([0-8]?[0-9](\\.[0-9]+)?)|90),"
            + "\\-?((1(([0-7]?[0-9](\\.[0-9]+)?)|80))|([0-9]?[0-9](\\.[0-9]+)?))";

    protected static final int SEARCH_RADIUS_KM=15;
    protected static final int SEARCH_RADIUS_M=SEARCH_RADIUS_KM*1000;
    
    public JSONObject getResponse(String url) {
        HttpURLConnection conn;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.disconnect();

            StringBuilder sb = new StringBuilder();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try ( // Get the response
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                }
            }
            
            String s = sb.toString();
            return new JSONObject(s);

        } catch (IOException | JSONException e) {
            return null;
        }
    }
    
    @Override
    public double getElevationDifferenceBetweenTwoLocations(String loc1, String loc2) {
        GeoRefServiceDTO loc1value = getElevation(loc1);
        GeoRefServiceDTO loc2value = getElevation(loc2);
        double finalvalue = loc1value.height-loc2value.height;
        return finalvalue;
    }
}
