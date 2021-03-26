package core.applicationSetup;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the application settings.
 *
 * @author Paulo Gandra Sousa
 */
public class AppSettings {

    private static final String PROPERTIES_RESOURCE = "app.properties";
    private static final String REPOSITORY_FACTORY_KEY = "persistence.repositoryFactory";
    private static final String PERSISTENCE_UNIT_KEY = "persistence.persistenceUnit";
    private static final String GEOREFERENCE_KEY = "georeference.service";
    private static final String SCHEMA_GENERATION_KEY = "javax.persistence.schema-generation.database.action";
   
        
    private final Properties applicationProperties = new Properties();

    public AppSettings() {
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
            Logger.getLogger(AppSettings.class.getName()).log(Level.SEVERE, null, exio);
        }
    }

    private void setDefaultProperties() {
        this.applicationProperties.setProperty(REPOSITORY_FACTORY_KEY,
                "core.persistence.jpa.JPARepositoryFactory");
        this.applicationProperties.setProperty(PERSISTENCE_UNIT_KEY, "persistence.persistenceUnit");
    }



    public String getPersistenceUnitName() {
        return this.applicationProperties.getProperty(PERSISTENCE_UNIT_KEY);
    }

    public String getRepositoryFactory() {
        return this.applicationProperties.getProperty(REPOSITORY_FACTORY_KEY);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getExtendedPersistenceProperties() {
        final Map ret = new HashMap();
        ret.put(SCHEMA_GENERATION_KEY,
                this.applicationProperties.getProperty(SCHEMA_GENERATION_KEY));
        return ret;
    }

    /**
     * Retorna o nome da classe de georeferenciação a ser utilizada
     * @return 
     */
    public String getGeoreferenceService(){
        System.out.println(this.applicationProperties.getProperty(GEOREFERENCE_KEY));
        return this.applicationProperties.getProperty(this.applicationProperties.getProperty(GEOREFERENCE_KEY));
    }
    
    public String getProperty(String prop) {
        return this.applicationProperties.getProperty(prop);
    }
}
