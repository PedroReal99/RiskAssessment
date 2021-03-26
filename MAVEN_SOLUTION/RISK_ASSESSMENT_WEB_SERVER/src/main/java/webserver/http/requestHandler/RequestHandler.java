/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import webserver.applicationSetup.ApplicationSE;
import webserver.http.HTTPmessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import webserver.http.Config;
import webserver.utils.FormatParser;
import webserver.utils.TextFormat;

/**
 *
 * @author CarloS
 */
public interface RequestHandler {

    public HTTPmessage handle(HTTPmessage request);

    default boolean findKey(String givenKey) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            String keyPath=ApplicationSE.settings().getKeyPath();
            Document keys = builder.parse(this.getClass().getClassLoader().getResourceAsStream(keyPath));
            NodeList keyList = keys.getDocumentElement().getElementsByTagName("key");
            for (int i = 0; i < keyList.getLength(); i++) {
                String key = "";
                key += ((Element) keyList.item(i)).getElementsByTagName("value").item(0).getTextContent();
                if (key.equals(givenKey)) {
                    return true;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            //
        }
        return false;
    }

    /**
     * Retorna um mapa com os headers relevantes, Authorization (key) e Content-Type (format), 
     * preenchendo a mensagem de resposta, em caso ocorra um erro
     * @param request
     * @param response
     * @return o mapa conseguido, ou null, caso o token nao seja válido
     */
    default Map<String, String> handleHttpExceptions(HTTPmessage request, HTTPmessage response) {

        Map<String, String> parameters = new HashMap<>();

        String key = request.getHeader().get("Authorization");
        if (key == null || !findKey(key)) {
            System.out.println("unrecognized key" + key);
            response.setResponseStatus(Config.UNAUTHORIZED);
            response.setHeader(HTTPmessage.WWW_AUTHENTICATE, "Basic realm=\"key");
            return null;
        }
        parameters.put("key", key);

        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            TextFormat format = FormatParser.getTextFormat(request.getContentType());

            if (format == TextFormat.NOTYPE) {
                response.setResponseStatus(Config.BAD_REQUEST);
                System.out.println("BAD_REQUEST");
                return null;
            }

            parameters.put("format", format.name());
        }
        
        return parameters;
    }
}
