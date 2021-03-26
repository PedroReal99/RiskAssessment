/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.utils;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import core.domain.location.GPSLocation;
import core.domain.Surrounding.STName;
import core.domain.Surrounding.Surrounding;
import core.domain.Surrounding.SurroundingName;
import georeference.GeoreferenceServiceController;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author joaoflores
 */
public class RegisterSurroundingByLocation {

    public RegisterSurroundingByLocation() {
        //do nothing
    }

    public Surrounding obtainSurroundingByLocation(String category, String stName, String address) throws IOException, ParseException {
        GeoRefServiceDTO externalGeoRefDTO = new GeoreferenceServiceController().getFullLocation(address);
        double latitude = externalGeoRefDTO.latitude ;
        double longitude = externalGeoRefDTO.longitude;
        float height = (float)externalGeoRefDTO.height;
        GPSLocation gPSLocation = new GPSLocation((float)latitude, (float)longitude, height, stName);
        Surrounding surrounding = new Surrounding( new SurroundingName(stName),gPSLocation, new STName(category));
        return surrounding;
    }

}
