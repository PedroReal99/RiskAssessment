/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import eapli.framework.util.Factory;
import georeference.GeoreferenceServiceController;

/**
 *
 * @author CarloS
 */
public class LocationFactory implements Factory<Location> {

    public static enum Type {
        GPS, POST
    }

    private final Type tipo;
    
    private final String location;
    
    public LocationFactory(Type tipo, String location) {
        this.tipo = tipo;
        this.location=location;
    }

    @Override
    public Location build() {
        GeoRefServiceDTO dto=new GeoreferenceServiceController().getFullLocation(location);
        switch(tipo){
            case GPS: 
                return new GPSLocation((float)dto.latitude,(float) dto.longitude, 0, dto.district);
            case POST:
                return new PostLocation(dto.country, dto.district, dto.road, ""+dto.number, dto.post_code);
            default:
                throw new IllegalArgumentException("LocationType not recognized");
        }
    } 

}
