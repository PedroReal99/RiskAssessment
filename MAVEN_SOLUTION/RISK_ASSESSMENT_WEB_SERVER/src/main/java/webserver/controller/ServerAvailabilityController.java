/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.controller;

import java.util.ArrayList;
import java.util.List;
import webserver.applicationSetup.AppSettingsSE;
import webserver.http.HTTPConnector;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ServerAvailabilityController {
    
    private static final String ACCEPTED = "Available";
    
    private static final String DENIED = "Not Available";
    
    AppSettingsSE settings = new AppSettingsSE();
    
    public List<String> serverLoadAndAvailibility() {
        List<String> list = new ArrayList<>();
        list.add(convertAvailibiltyBooleanToString());
        list.add(String.valueOf(obtainServerCurrentLoad()));
        list.add(String.valueOf(obtainServerCurrentLoadPerMinute()));
        return list;
    }
    
    protected int obtainServerCurrentLoad() {
        return HTTPConnector.obtainThreadController().getLoad();
    }
    
    protected int obtainServerCurrentLoadPerMinute() {
        return HTTPConnector.obtainThreadController().getLoadPerMinute();
    }
    
    protected boolean obtainServerCurrentAvailability() {
        return ((obtainServerCurrentLoad() >= settings.getMaxRequestForServer()) || (obtainServerCurrentLoadPerMinute() >= settings.getMaxRequestPerMinuteForServer()));
    } 
    
    protected String convertAvailibiltyBooleanToString() {
        if(obtainServerCurrentAvailability()) {
            return DENIED;
        }
        return ACCEPTED;
    }
}
