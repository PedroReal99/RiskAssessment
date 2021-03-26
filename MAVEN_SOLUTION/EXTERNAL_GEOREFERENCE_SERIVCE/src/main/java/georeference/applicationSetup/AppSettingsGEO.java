package georeference.applicationSetup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the application settings.
 *
 * @author Paulo Gandra Sousa
 */
public class AppSettingsGEO {

    private static final String PROPERTIES_RESOURCE = "appGEO.properties";
    public static final String SELECTED_API = "georeference.service.api.selected";


    private final Properties applicationProperties = new Properties();
       public AppSettingsGEO() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream propertiesStream = this.getClass().getClassLoader()
                .getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (propertiesStream != null) {
                this.applicationProperties.load(propertiesStream);
            } else {
                throw new FileNotFoundException(
                        "property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
            }
            
        } catch (final IOException exio) {
            Logger.getLogger(AppSettingsGEO.class.getName()).log(Level.SEVERE, null, exio);
        }
    }

    public String getProperty(String prop) {
        return this.applicationProperties.getProperty(prop);
    }
}
