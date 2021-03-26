/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import core.domain.Case.CasePriority;
import webserver.utils.FormatParser;
import webserver.utils.TextFormat;
import webserver.controller.RASubmissionController;
import webserver.http.Config;
import webserver.http.HTTPmessage;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
 * @author CarloS
 */
public class RASubmissionRequestHandler implements RequestHandler {

    @Override
    public HTTPmessage handle(HTTPmessage request) {
        StatsController.associateBegining(request,Date.from(Instant.now()));
        HTTPmessage response = new HTTPmessage();

        Map<String, String> parameters = handleHttpExceptions(request, response);
        if (parameters == null) {
            return response;
        }

        String needsValidation = request.getURI().getQueryValues().get("needsValidation");
        boolean needOfValidation = needsValidation != null && needsValidation.equalsIgnoreCase("yes");

        String content = request.getContentAsString();
        TextFormat format = TextFormat.valueOf(parameters.get("format"));

        if (format != TextFormat.XML) {
            content = FormatParser.convert(format, TextFormat.XML, content);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content)));
            Element keysElement = doc.getDocumentElement();
            if (!keysElement.getTagName().equals("request")) {
                keysElement = (Element) keysElement.getElementsByTagName("request").item(0);
            }

            RASubmissionController raSubmmissionController = new RASubmissionController(needOfValidation, CasePriority.MAX);

            NodeList iObjects = keysElement.getElementsByTagName("insuranceObject");
            parseInsuranceObjects(raSubmmissionController, iObjects);
            raSubmmissionController.save();

            response.setResponseStatus(Config.CREATED);
            System.out.println("CREATED");
            return response;

        } catch (SAXException | IOException | ParserConfigurationException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            response.setResponseStatus(Config.INTERNAL_ERROR);
            System.out.println("INTERNAL ERROR");
            return response;
        }
    }

    public static void parseInsuranceObjects(RASubmissionController raSubmmissionController, NodeList iObjects) {
        for (int i = 0; i < iObjects.getLength(); i++) {
            Element iObjectElement = (Element) iObjects.item(i);
            String location = iObjectElement.getElementsByTagName("location").item(0).getTextContent();
            NodeList coveragesList = iObjectElement.getElementsByTagName("coverage");
            Set<String> coverages = new HashSet<>();
            for (int j = 0; j < coveragesList.getLength(); j++) {
                Element coverageElement = (Element) coveragesList.item(j);
                coverages.add(coverageElement.getTextContent());
            }
            raSubmmissionController.addInsuranceObject(location, coverages);
        }

    }

}
