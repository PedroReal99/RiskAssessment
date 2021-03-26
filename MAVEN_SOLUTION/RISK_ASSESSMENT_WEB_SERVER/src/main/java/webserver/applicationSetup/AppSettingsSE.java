package webserver.applicationSetup;

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
public class AppSettingsSE {

    private static final String PROPERTIES_RESOURCE = "appSE.properties";
    private static final String CLASS_LOADER_KEY = "classLoader";
    private static final String APP_KEYS_KEY = "keys";
    private static final String MAX_REQUESTS = "maxRequests";
    private static final String MAX_REQUESTS_PER_MINUTE = "maxRequestPerMinute";
    private static final String KEYSTORE="keyStore";
    private static final String KEYSTORE_PASSWORD="keyStorePassword";
    
    private final Properties applicationProperties = new Properties();

    public AppSettingsSE() {
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
            setDefaultProperties();
            Logger.getLogger(AppSettingsSE.class.getName()).log(Level.SEVERE, null, exio);
        }
    }

    private void setDefaultProperties() {
        this.applicationProperties.setProperty(CLASS_LOADER_KEY,
                "knownHandlers.xml");
        this.applicationProperties.setProperty(APP_KEYS_KEY,
                "keys.xml");
    }

    public String getClassLoaderPath() {
        return this.applicationProperties.getProperty(CLASS_LOADER_KEY);
    }

    public String getKeyPath() {
        return this.applicationProperties.getProperty(APP_KEYS_KEY);
    }

    public String getProperty(String prop) {
        return this.applicationProperties.getProperty(prop);
    }

    public int getMaxRequestForServer() {
        return Integer.parseInt(this.applicationProperties.getProperty(MAX_REQUESTS));
    }

    public int getMaxRequestPerMinuteForServer() {
        return Integer.parseInt(this.applicationProperties.getProperty(MAX_REQUESTS_PER_MINUTE));
    }
    
    public String getKeyStore(){
        return this.applicationProperties.getProperty(KEYSTORE);
    }
    
    public String getKeyStorePassword(){
        return this.applicationProperties.getProperty(KEYSTORE_PASSWORD);
    }
}
