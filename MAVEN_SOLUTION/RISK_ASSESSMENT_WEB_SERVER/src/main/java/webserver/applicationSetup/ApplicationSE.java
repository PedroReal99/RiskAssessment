package webserver.applicationSetup;

import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * A "global" static class with the application registry of well known objects
 *
 * @author Paulo Gandra Sousa
 *
 */
public class ApplicationSE {

    private static final AppSettingsSE SETTINGS = new AppSettingsSE();

    public static AppSettingsSE settings() {
        return SETTINGS;
    }

    private ApplicationSE() {
        // private visibility to ensure singleton & utility
    }
}
