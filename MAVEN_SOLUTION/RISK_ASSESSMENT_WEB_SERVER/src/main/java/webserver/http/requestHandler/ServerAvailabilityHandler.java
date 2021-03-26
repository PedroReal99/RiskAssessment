/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import webserver.controller.ServerAvailabilityController;
import webserver.controller.StatsController;
import webserver.http.Config;
import webserver.http.HTTPmessage;
import webserver.utils.ExportUtil;

/**
 *
 * @author Vasco_Rodrigues
 */
public class ServerAvailabilityHandler implements RequestHandler {
    
    private static final String XSD_FILE_NAME = "SCHEMA_SE06.xsd";
    
    private static final String XSL_FILE_NAME = "SE06_XML_XHTML.xsl";
    
    private static final String JSON_FILE_NAME = "SE06.json";
    
    private static final String XML_FILE_NAME = "SE06.xml";
    
    private static final String XHTML_FILE_NAME = "SE06.xhtml";
    
    private static final String FUNC = "SE06";

    @Override
    public HTTPmessage handle(HTTPmessage request) {
        StatsController.associateBegining(request,Date.from(Instant.now()));
        HTTPmessage response = new HTTPmessage();
        Map<String, String> parameters = handleHttpExceptions(request, response);
        if (parameters == null) {
            return response;
        }
        String exportfiletype = request.getURI().getQueryValues().get("export").toUpperCase();
        List<String> aux = new ServerAvailabilityController().serverLoadAndAvailibility();
        String str = buildInformationToString(aux);
        try {
            File f = exportIntoDesiredFileType(exportfiletype, str);
            buildResponse(response, f);
        } catch (IOException ex) {
            Logger.getLogger(ServerAvailabilityHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Get");
        return response;
    }
    
    protected String buildInformationToString(List<String> aux) {
        StringBuilder str = new StringBuilder(); 
        str.append("Server ");
        str.append(aux.get(0));
        str.append("\n");
        str.append(" with load = ");
        str.append(aux.get(1));
        str.append("\n");
        str.append(" and load per minute = ");
        str.append(aux.get(2));
        return str.toString();
    }
    
    protected File exportIntoDesiredFileType(String filetype, String content) throws IOException {
        ExportUtil ex = new ExportUtil();
        switch(filetype) {
            case "XML":
                File exported = ex.transformIntoXML(content, XML_FILE_NAME, FUNC, "Result");
                if(ex.validateXML(exported, XSD_FILE_NAME)) {
                    return exported;
                } else {
                    return null;
                }
            case "JSON":
                ex.exportToJSon(content, XML_FILE_NAME, JSON_FILE_NAME, FUNC, "Result");
                return new File(JSON_FILE_NAME);
                
            case "XHTML":    
                ex.exportToXHTML(content, XML_FILE_NAME, XSD_FILE_NAME, XHTML_FILE_NAME, XSL_FILE_NAME, FUNC, "Result");
                return new File(XHTML_FILE_NAME);
                
            default: return null;      
        }
    }
    
    protected void buildResponse(HTTPmessage response, File file) {
        response.setContentFromFile(file.getName());
        response.setResponseStatus(Config.POSITIVE_RESPONSE);
    }
    
}
