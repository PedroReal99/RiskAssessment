/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package georeference;

import com.mycompany.georeference_interface.TravelMethod;
import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.GeoreferenceService;
import georeference.adapter.GoogleGeoRefAdapter;
import georeference.applicationSetup.AppSettingsGEO;
import georeference.applicationSetup.ApplicationGEO;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author CarloS
 */
public class GeoreferenceServiceController implements GeoreferenceService {

    @Override
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod type) {
        AppSettingsGEO set = ApplicationGEO.settings();
        String apis[] = set.getProperty(AppSettingsGEO.SELECTED_API).split("\\|");
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        for (String api : apis) {
            String selected = set.getProperty(api);
            try {
                dto.merge(((ApiGeoreferenceService) Class.forName(selected).newInstance()).getDistanceAndDuration(from, to, type));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                System.out.println("Unexpected: " + ex.getMessage());;
            }
        }
        return dto;
    }

    @Override
    public GeoRefServiceDTO getFullLocation(String param) {
        AppSettingsGEO set = ApplicationGEO.settings();
        String apis[] = set.getProperty(AppSettingsGEO.SELECTED_API).split("\\|");
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        for (String api : apis) {
            String selected = set.getProperty(api);
            try {
                dto.merge(((ApiGeoreferenceService) Class.forName(selected).newInstance()).getFullLocation(param));

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                System.out.println("Unexpected: " + ex.getMessage());;
            }
        }
        return dto;
    }

    @Override
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find) {
        AppSettingsGEO set = ApplicationGEO.settings();
        String apis[] = set.getProperty(AppSettingsGEO.SELECTED_API).split("\\|");
        Set<GeoRefServiceDTO> results = new HashSet<>();

        for (String api : apis) {
            String selected = set.getProperty(api);
            try {
                results.addAll(((ApiGeoreferenceService) Class.forName(selected).newInstance()).getSurroundings(center, find));

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                System.out.println("Unexpected: " + ex.getMessage());;
            }
        }
        return results;

    }

    @Override
    public GeoRefServiceDTO getElevation(String param) {
        AppSettingsGEO set = ApplicationGEO.settings();
        String apis[] = set.getProperty(AppSettingsGEO.SELECTED_API).split("\\|");
        GeoRefServiceDTO dto = new GeoRefServiceDTO();

        for (String api : apis) {
            String selected = set.getProperty(api);
            try {
                dto.merge(((ApiGeoreferenceService) Class.forName(selected).newInstance()).getElevation(param));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                System.out.println("Unexpected: " + ex.getMessage());;
            }
        }
        return dto;
    }
    
    @Override
    public double getElevationDifferenceBetweenTwoLocations(String loc1, String loc2) {
        GeoRefServiceDTO loc1value = getElevation(loc1);
        GeoRefServiceDTO loc2value = getElevation(loc2);
        double finalvalue = loc1value.height-loc2value.height;
        return finalvalue;
    }

    /**
     *
     * @param address address if the surrounding or the coordenates
     * @return true if runs correctly false otherwise
     * @throws IOException
     */
    public boolean openStaticImage(String address) {
        try {
            return ((GoogleGeoRefAdapter) Class.forName("georeference.adapter.GoogleGeoRefAdapter").newInstance()).openStaticImage(address);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
