/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utils;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import com.mycompany.georeference_interface.GeoreferenceService;
import com.mycompany.georeference_interface.TravelMethod;
import core.applicationSetup.Application;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author CarloS
 */
public class GeoReferenceUtil implements GeoreferenceService {

    private final GeoreferenceService service;

    public GeoReferenceUtil() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        service = (GeoreferenceService) Class.forName(Application.settings().getGeoreferenceService()).newInstance();
    }

    @Override
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod method) {
        return service.getDistanceAndDuration(from, to, method);
    }

    @Override
    public GeoRefServiceDTO getFullLocation(String param) {
        return service.getFullLocation(param);
    }

    @Override
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find) {
        return service.getSurroundings(center, find);
    }

    public List<String> getSurroundingsAsString(String center, String find) {
        List<String> stringList = new ArrayList<>();
        for (GeoRefServiceDTO dto : service.getSurroundings(center, find)) {
            stringList.add(dto.name);
        }
        return stringList;
    }

    @Override
    public GeoRefServiceDTO getElevation(String param) {
        return service.getElevation(param);
    }

    @Override
    public double getElevationDifferenceBetweenTwoLocations(String loc1, String loc2) {
        GeoRefServiceDTO loc1value = getElevation(loc1);
        GeoRefServiceDTO loc2value = getElevation(loc2);
        double finalvalue = loc1value.height - loc2value.height;
        return finalvalue;
    }

}
