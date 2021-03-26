/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.mycompany.georeference_interface.GeoRefServiceDTO;
import georeference.GeoreferenceServiceController;
import java.io.IOException;
import com.mycompany.georeference_interface.TravelMethod;
import java.util.Set;
import georeference.ApiGeoreferenceService;

/**
 *
 * @author joaoflores
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        new GeoreferenceServiceController().openStaticImage("41,-8");
        
        //System.out.println("41.12,-8.3".matches(GeoreferenceService.COORD_EXP));
//        Set<ExternalGeoRefServiceDTO> dtos = new GeoreferenceServiceController().getSurroundings("Rua tildevesa 20 maia", "policia");
////     ExternalGeoRefServiceDTO dto = new GeoreferenceServiceController().getDistanceAndDuration("Rua tildevesa 20 maia","ISEP Porto",TravelMethod.DRIVING);
////        ExternalGeoRefServiceDTO dto = new GeoreferenceServiceController().getFullLocation("Rua tildevesa 20 maia");
//
//        for (ExternalGeoRefServiceDTO dto : dtos) {
//            System.out.println(dto.name);
//        }
//        String testCord="90,-97";
//        String expr="\\-?(([0-8][0-9](\\.[0-9]+)?)|90),"
//                + "\\-?((1(([0-7][0-9](\\.[0-9]+)?)|80))|([0-9][0-9](\\.[0-9]+)?))";
//        System.out.println(testCord.matches(expr));
//        
//        System.out.println(dto.country);
//        System.out.println(dto.distance);
//        System.out.println(dto.district);
//        System.out.println(dto.height);
//        System.out.println(dto.latitude);
//        System.out.println(dto.longitude);
//        System.out.println(dto.number);
//        System.out.println(dto.post_code);
//        System.out.println(dto.road);
//        System.out.println(dto.duration);
//        ExternalGeoRefServiceController egc = new ExternalGeoRefServiceController();
//        List<String> gps = egc.obtainGPSCoordenates("google", "1600, Amphitheatre Parkway", "geocode");
//        gps.toString();
//
//        String apiName = "google";
//        List<String> postLocation = egc.obtainDetailInfoFromPostLocation(apiName, "1600, Amphitheatre Parkway", "geocode");
//        System.out.println(postLocation.toString());
//        String location1 = "1600, Amphitheatre Parkway";
//        String location2 = "1, Quarry road, CA";
//        String mode = "driving";
//        List<Double> distanceAndDuration = egc.obtainDistanceAndDuration(apiName, Param.createParam(apiName, "distancematrix", location1), Param.createParam(apiName, "distancematrix", location2), mode, "distancematrix");
//        System.out.println(location1 + " to " + location2 + " Distance: " + distanceAndDuration.get(0) + " Time " + mode + ": " + distanceAndDuration.get(1));
    }

}
