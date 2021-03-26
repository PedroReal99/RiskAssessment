package georeference.applicationSetup;

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
public class ApplicationGEO {

    private static final AppSettingsGEO SETTINGS = new AppSettingsGEO();

    public static AppSettingsGEO settings() {
        return SETTINGS;
    }

    private ApplicationGEO() {
        // private visibility to ensure singleton & utility
    }
}
