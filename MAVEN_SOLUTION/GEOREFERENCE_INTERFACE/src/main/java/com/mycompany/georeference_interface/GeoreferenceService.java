/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.georeference_interface;

import java.util.Set;

/**
 *
 * @author CarloS
 */
public interface GeoreferenceService {

    
    public GeoRefServiceDTO getDistanceAndDuration(String from, String to, TravelMethod method);

    /**
     * Completes a location given a reference for it, a reference can either be
     * post address or gps location
     *
     * @param param reference
     * @return DTO containing all the elements found to that location
     */
    public GeoRefServiceDTO getFullLocation(String param);

    /**
     * Finds, for a location, the nearby surroundings of the specified type, note, for better accuracy, add a query to respective surrounding.properties file
     * @param center location reference
     * @param find type of surroundings to be found
     * @return a list with the found surroundings
     */
    public Set<GeoRefServiceDTO> getSurroundings(String center, String find);
    
    /**
     * Finds the elevation for a given location
     *
     * @param param location reference
     * @return DTO containing the elevation for the given location or point
     */
    public GeoRefServiceDTO getElevation(String param);
    
    public double getElevationDifferenceBetweenTwoLocations(String loc1, String loc2);
}
