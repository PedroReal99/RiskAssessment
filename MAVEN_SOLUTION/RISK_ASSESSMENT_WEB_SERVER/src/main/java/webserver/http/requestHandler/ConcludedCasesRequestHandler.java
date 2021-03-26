/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http.requestHandler;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.Insurance.InsuranceObject;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import webserver.controller.ConcludedCasesController;
import webserver.controller.GetRiskAssessmentResultController;
import webserver.controller.StatsController;
import webserver.http.Config;
import webserver.http.HTTPmessage;
import webserver.utils.ExportUtil;
import webserver.utils.FormatParser;
import webserver.utils.TextFormat;

/**
 *
 * @author joaoflores
 */
public class ConcludedCasesRequestHandler implements RequestHandler {

    private static final String XSD_FILE_NAME = "SCHEMA_SE03.xsd";
    private static final String XSD_INPUT_FILE_NAME = "SCHEMA_INPUT_SE03.xsd";
    private static final String XSL_FILE_NAME = "SE03_XML_XHTML.xsl";
    private static final String JSON_FILE_NAME = "SE03.json";
    private static final String XML_FILE_NAME = "SE03.xml";
    private static final String XHTML_FILE_NAME = "SE03.xhtml";
    private static final String FUNC = "SE03";
    private static ConcludedCasesController concludedCasesController = new ConcludedCasesController();
    private GetRiskAssessmentResultController assessmentResultController = new GetRiskAssessmentResultController();

    @Override
    public HTTPmessage handle(HTTPmessage request) {
        StatsController.associateBegining(request,Date.from(Instant.now()));
        HTTPmessage response = new HTTPmessage();
        ExportUtil ex = new ExportUtil();
        Map<String, String> parameters = handleHttpExceptions(request, response);
        if (parameters == null) {
            return response;
        }

        String exportfiletype = request.getURI().getQueryValues().get("export").toUpperCase();
        String content = request.getContentAsString();
        TextFormat format = TextFormat.valueOf(parameters.get("format"));
        String matrixVersion = "";
        if (format != TextFormat.XML) {
            content = FormatParser.convert(format, TextFormat.XML, content);
        }
        // File input = new File("input.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content)));
            java.io.Writer writer = new java.io.FileWriter("input.xml");
            XMLSerializer xml = new XMLSerializer(writer, null);
            xml.serialize(doc);

            if (!ex.validateXML(new File("input.xml"), XSD_INPUT_FILE_NAME)) {
                response.setResponseStatus(Config.INTERNAL_ERROR);
                System.out.println("File does not follow the rules!!");
                return response;
            } else {

                Element keysElement = doc.getDocumentElement();
                if (!keysElement.getTagName().equals("request")) {
                    keysElement = (Element) keysElement.getElementsByTagName("request").item(0);
                }

                NodeList iObjects = keysElement.getElementsByTagName("parametersFromRequest");
                
                List<CaseI> cases = parseCompletedCases(iObjects, matrixVersion);

                if (matrixVersion.isEmpty()) {
                    matrixVersion = "1";
                }
                String casesInformation = buildCasesInformationToString(cases, matrixVersion);

                File file = exportIntoDesiredFileType(exportfiletype, casesInformation);
                buildResponse(response, file, Config.POSITIVE_RESPONSE);
                System.out.println("POSITIVE RESPONSE!!");
                return response;
            }
        } catch (SAXException | IOException | ParserConfigurationException | IndexOutOfBoundsException ex1) {
            ex1.printStackTrace();
            response.setResponseStatus(Config.INTERNAL_ERROR);
            System.out.println("INTERNAL ERROR!!");
            return response;
        } catch (java.text.ParseException ex2) {
            Logger.getLogger(ConcludedCasesRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        // }
        return response;
    }

    public static List<CaseI> parseCompletedCases(NodeList iObjects, String matrixVersion) throws java.text.ParseException {

        String stateOfCase = "";
        String startDate = "";
        String finalDate = "";

        CaseDate inicialDate = null;
        CaseDate endDate = null;

        CaseState state = null;

        List<String> cities = new ArrayList<>();

        for (int i = 0; i < iObjects.getLength(); i++) {
            Element iObjectElement = (Element) iObjects.item(i);
            //state of the case
            matrixVersion = iObjectElement.getElementsByTagName("matrixVersion").item(0).getTextContent();
            //state of the case
            stateOfCase = iObjectElement.getElementsByTagName("state").item(0).getTextContent();
            if (stateOfCase.equalsIgnoreCase(CaseState.PROCESSED.toString())) {
                state = CaseState.PROCESSED;
            }

            try {
                //start date from the request
                startDate = iObjectElement.getElementsByTagName("initialDate").item(0).getTextContent();
                inicialDate = new CaseDate(startDate);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                startDate = "";
                inicialDate = new CaseDate(startDate);
                try {
                    // end date from the request
                    finalDate = iObjectElement.getElementsByTagName("finalDate").item(0).getTextContent();
                    endDate = new CaseDate(finalDate);
                } catch (NullPointerException ex1) {
                    ex1.printStackTrace();
                    if (!inicialDate.toString().equals("")) {
                        endDate = CaseDate.now();
                    } else {
                        finalDate = "";
                        endDate = new CaseDate(finalDate);
                    }
                }
            }
            if (endDate == null) {
                endDate = new CaseDate("");
            }

            try {

                // add the cities to a list if they exist
                NodeList cityList = iObjectElement.getElementsByTagName("cities");

                for (int j = 0; j < cityList.getLength(); j++) {
                    Element cityElement = (Element) cityList.item(j);
                    cities.add(cityElement.getTextContent());
                }
            } catch (NullPointerException ex2) {
                ex2.printStackTrace();
                cities = new ArrayList<>();
            }

        }
        return casesSatisfingRequest(state, inicialDate, endDate, cities);
    }

    public static List<CaseI> casesSatisfingRequest(CaseState stateOfCase, CaseDate startDate, CaseDate finalDate, List<String> cities) throws java.text.ParseException {
        List<CaseI> allCases = concludedCasesController.getAllCases();
        List<CaseI> allCasesByState = new ArrayList<>();
        for (CaseI c : allCases) {
            if (c.obtainState().equalsIgnoreCase(CaseState.PROCESSED.toString())) {
                allCasesByState.add(c);
            }
        }
        if (startDate.toString().equals("") && finalDate.toString().equals("") && !cities.isEmpty()) {
            return getCaseResultsFilteredByCities(cities, allCasesByState);
        } else if (cities.isEmpty() && !startDate.equals("") && !finalDate.equals("")) {
            return getCaseResultsFilteredByTimeInterval(startDate, finalDate, allCasesByState);
        } else if (!cities.isEmpty() && !startDate.equals("") && !finalDate.equals("")) {
            return getCaseResultsFilteredByTimeIntervalAndCities(startDate, finalDate, cities, allCasesByState);
        } else {
            return new ArrayList<>();
        }
    }

    public static List<CaseI> getCaseResultsFilteredByCities(List<String> cities, List<CaseI> allCasesByState) {
       return concludedCasesController.getCaseResultsFilteredByCities(cities, allCasesByState);
    }

    public static List<CaseI> getCaseResultsFilteredByTimeInterval(CaseDate startDate, CaseDate finalDate, List<CaseI> allCasesByState) throws java.text.ParseException {       
       return concludedCasesController.getCaseResultsFilteredByTimeInterval(startDate,finalDate,allCasesByState);
    } 
    
    public  static List<CaseI> getCaseResultsFilteredByTimeIntervalAndCities(CaseDate startDate, CaseDate finalDate, List<String> cities, List<CaseI> allCasesByState) throws java.text.ParseException {
      return concludedCasesController.getCaseResultsFilteredByTimeIntervalAndCities( startDate,  finalDate,cities, allCasesByState);
    }

    private String buildCasesInformationToString(List<CaseI> cases, String matrixVersion) {
        StringBuilder infoFromCases = new StringBuilder();
        infoFromCases.append("Cases:");
        for (CaseI concludedCase : cases) {
            String result = assessmentResultController.getIntegerRiskIndex(concludedCase.obtainCode(), matrixVersion);
            infoFromCases.append("\n");
            infoFromCases.append("Case Information:");
            infoFromCases.append("\n");
            infoFromCases.append("Identifier:");
            infoFromCases.append(concludedCase.obtainCode());
            infoFromCases.append("\n");
            infoFromCases.append("State:");
            infoFromCases.append(concludedCase.obtainState());
            infoFromCases.append("\n");
            infoFromCases.append("Result");
            infoFromCases.append("\n");
            infoFromCases.append(result.trim());
            infoFromCases.append("\n");
        }
        return infoFromCases.toString();
    }

    protected void buildResponse(HTTPmessage response, File file, String status) {
        response.setContentFromFile(file.getName());
        response.setResponseStatus(status);
    }

    protected File exportIntoDesiredFileType(String filetype, String content) throws IOException {
        ExportUtil ex = new ExportUtil();
        switch (filetype) {
            case "XML":
                File exported = ex.transformIntoXML(content, XML_FILE_NAME, FUNC, "Result");
                if (ex.validateXML(exported, XSD_FILE_NAME)) {
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
            default:
                return null;
        }
    }

}
