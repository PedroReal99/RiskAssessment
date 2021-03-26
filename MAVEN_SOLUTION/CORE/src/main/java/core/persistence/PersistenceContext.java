/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.persistence;

import core.applicationSetup.Application;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * provides easy access to the persistence layer. works as a factory of
 * factories
 *
 * @author Paulo Gandra Sousa
 */
public class PersistenceContext {

    private static volatile RepositoryFactory theFactory;

    private PersistenceContext() {
    }

    public static RepositoryFactory repositories() {
        final String factoryClassName = Application.settings().getRepositoryFactory();
        if (theFactory == null) {
            try {
                //updateDirectory();
                theFactory = (RepositoryFactory) Class.forName(factoryClassName).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return theFactory;
    }

    /*private static void updateDirectory() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        if(getDatabaseDirectory().contains(".m2")){
            return;
        }
        //como a usar o JPA não é possível decidir o diretorio da BD em runtime,
        //de modo a que todos os módulos sejam executáveis independente do diretório,
        //é alterado o diretório para a base do projeto
        System.setProperty("user.dir", getResourceDirectory("META-INF/persistence.xml"));
        
    }

    private static String getDatabaseDirectory() {
        File file = new File(PersistenceContext.class.getClassLoader().getResource("app.properties").getFile());
        String path = "jdbc:h2:"+file.getParentFile().getParentFile().getParentFile().getParent();
        path += "/databases/InsuranceProject";
        return path.replace("file:", "");
    }
    
    private static String getResourceDirectory(String resource) throws IOException {
        File file = new File(PersistenceContext.class.getClassLoader().getResource(resource).getFile());
        String path = file.getParentFile().getParentFile().getParentFile().getParentFile().getPath();
        return path.replaceAll("file:", "").replaceAll("%20", " ");
    }*/
}
