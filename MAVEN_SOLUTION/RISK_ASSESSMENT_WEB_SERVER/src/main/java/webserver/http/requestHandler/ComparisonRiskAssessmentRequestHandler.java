/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import webserver.utils.FormatParser;
import webserver.utils.TextFormat;
import webserver.utils.FileReceiver;
import webserver.controller.ComparisonRiskAssessmentController;
import webserver.applicationSetup.ApplicationSE;
import webserver.http.Config;
import webserver.http.HTTPmessage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import webserver.controller.StatsController;

/**
 *
 * @author Pedro
 */
public class ComparisonRiskAssessmentRequestHandler implements RequestHandler {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    @Override
    public HTTPmessage handle(HTTPmessage request) {
        StatsController.associateBegining(request,Date.from(Instant.now()));
        HTTPmessage response = new HTTPmessage();
        File file = null;

        Map<String, String> parameters = handleHttpExceptions(request, response);
        if (parameters == null) {
            return response;
        }

        String content = request.getContentAsString();
        TextFormat type = FormatParser.getTextFormat(request.getContentType());
        FileReceiver fr = new FileReceiver();

        try {
            try {
                file = fr.fileToMethods(content, type,"SE04");
            } catch (Exception ex) {
                Logger.getLogger(ComparisonRiskAssessmentRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            //response.setContent("", type);
            response.setContentFromFile(file.getName());
            response.setResponseStatus(Config.CREATED);
            System.out.println("CREATED");
            return response;

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            response.setResponseStatus(Config.INTERNAL_ERROR);
            System.out.println("INTERNAL ERROR");
            return response;
        }
    }
}
