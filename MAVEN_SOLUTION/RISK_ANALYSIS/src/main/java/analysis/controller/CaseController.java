/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.controller;

import core.domain.Case.CaseCode;
import core.domain.Case.CaseDate;
import core.domain.Case.CaseI;
import core.domain.Case.CaseState;
import core.domain.User.Role;
import core.persistence.PersistenceContext;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author morei
 */
public class CaseController {

    private List<Long> timePerValidation = new ArrayList<>();

    public List<CaseI> listCaseByStateWithoutEmployee(CaseState state) {
        List<CaseI> i = PersistenceContext.repositories().caseRepository().findAllByState(state);
        return parseOrderByDate(i);
    }

    public List<CaseI> listCaseByState(CaseState state) {
        return PersistenceContext.repositories().caseRepository().findAllByState(state);
    }

    public List<CaseI> listCaseByStateWithDistrict(String state, String district) {
        List<CaseI> i = PersistenceContext.repositories().caseRepository().findAllCaseByStateWithDistrict(state, district);
        return parseOrderByDate(i);
    }

    public static void changeCaseAr(CaseCode caseId) {
        CaseI caseI = PersistenceContext.repositories().caseRepository().findByCaseCode(caseId);
        if (caseI == null) {
            return;
        }
        caseI.associateEmployee(LoginController.logged.identity().toString(),LoginController.logged.identity());
        caseI.alterDate(CaseDate.now().toString());
        PersistenceContext.repositories().caseRepository().save(caseI);
    }

    public static boolean verifyRole() {
        return (LoginController.logged.verifyRole(Role.RISK_ANALIST) || LoginController.logged.verifyRole(Role.ADMIN));
    }

    private List<CaseI> parseOrderByDate(List<CaseI> l) {
        List<CaseI> list = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getEmployee() == null) {
                list.add(l.get(i));
            }
        }
        //order(list);
        return list;
    }

    private void order(List<CaseI> l) {
        CaseI casei = null;
        for (int i = 0; i < l.size() - 1; i++) {
            for (int j = i + 1; j < l.size(); j++) {
                if (l.get(i).obtainDate().compareTo(l.get(j).obtainDate()) > 0) {
                    casei = l.get(i);
                    l.set(i, l.get(j));
                    l.set(j, casei);
                }
            }
        }
    }

    public List<CaseI> listCasesValidatedByAR(List<CaseDate> limitDates) {
        List<CaseI> allValidatedCases = listCaseByState(CaseState.PROCESSED);
        List<CaseI> userValidatedCases = filterUser(allValidatedCases);
        if (limitDates != null) {
            filterByTime(userValidatedCases, limitDates);
        }
        order(userValidatedCases);
        return userValidatedCases;
    }

    private void filterByTime(List<CaseI> userValidatedCases, List<CaseDate> limitDates) {
        List<Date> limitDatesDate = new ArrayList<>();
        List<CaseI> markedToRemove = new ArrayList<>();
        int cont = 0;
        for (CaseDate caseDate1 : limitDates) {
            limitDatesDate.add(stringToDate(caseDate1.toString()));
        }
        for (CaseI caseI : userValidatedCases) {
            String caseDate = caseI.obtainDate();
            Date caseDateDate = stringToDate(caseDate);
            if (limitDatesDate.get(0).after(caseDateDate) || limitDatesDate.get(1).before(caseDateDate)) {
                markedToRemove.add(caseI);
            }
            cont++;
        }
        for (CaseI i : markedToRemove) {
            userValidatedCases.remove(i);
        }
    }

    private List<CaseI> filterUser(List<CaseI> allValidatedCases) {
        List<CaseI> userValidatedCases = new ArrayList<>();
        for (CaseI caseI : allValidatedCases) {
            if (caseI.getEmployee()!= null && caseI.getEmployee().toString().equalsIgnoreCase(LoginController.logged.obtainEmail().toString())) {
                userValidatedCases.add(caseI);
            }
        }
        return userValidatedCases;
    }

    public String makeFormatedString(List<CaseI> userValidatedCases) {
        String present = "";
        for (CaseI caseI : userValidatedCases) {
            Date initDate = stringToDate(caseI.obtainAssignedDate().toString());
            Date finDate = stringToDate(caseI.obtainValidatedDate().toString());
            long seconds = finDate.getTime() - initDate.getTime();
            timePerValidation.add(seconds);
            present = present + "Case id: " + caseI.obtainCode() + " tempo decorrido: " + formatddHHMMSS(seconds) + ".\n";
        }
        return present;
    }

    private Date stringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            return formatter.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatddHHMMSS(long secondsCount) {
        //Calculate the seconds to display:
        long seconds = secondsCount % 60;
        secondsCount -= seconds;
        //Calculate the minutes:
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        //Calculate the hours:
        long hoursCount = minutesCount / 60;
        long hours = hoursCount % 24;
        //Calculate days
        long days = hoursCount / 24 / 1000;
        //Build the String
        return "" + days + " days " + hours + " hours " + minutes + " minutes and " + seconds + " seconds";
    }

    public String buildSummary() {
        long secondsAvg = calculateTimeAvg();
        return "" + LoginController.logged.obtainEmail().toString() + " validated " + timePerValidation.size() + " Cases and took an average of " + formatddHHMMSS(secondsAvg) + ".";
    }

    private long calculateTimeAvg() {
        long sum = 0;
        int cont = 0;
        double avg;
        for (long sec : timePerValidation) {
            sum += sec;
            cont++;
        }
        if (cont != 0) {
            avg = sum / cont;
            return (long) avg;
        }
        return 0;
    }

    public File convertToXHTML(String output, String summary, String fileName) {
        final String xmlStr = "<result>" + "\n<output>" + output + "</output>" +
                "\n<summary>" + summary + "</summary>" +
                "\n</result>";

        try {
            File file = stringToDom(xmlStr, fileName);
            if (validateXML("ar05Schema.xsd", file)) {
                return transformXMLToXHTML(file, fileName);
            } else {
                System.out.println("INVALID XML AR05");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    private boolean validateXML(String xsdfile, File file) {
        File schemaFile = new File(xsdfile); // etc. SchemaSE04Export.xsd
        Source xmlFile = new StreamSource(file);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            return true;
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
        } catch (IOException e) {
        }
        return false;
    }

    public static File stringToDom(String xmlSource, String fileName)
            throws SAXException, ParserConfigurationException, IOException, TransformerException {
        // Parse the given input
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File file = new File(fileName + ".xml");

        StreamResult result =  new StreamResult(file);
        transformer.transform(source, result);

        return file;
    }

    private File transformXMLToXHTML(File xmlfile, String fileName) {
        Source xmlInput = new StreamSource(xmlfile);
        Source xsl = new StreamSource("ar05xml2xhtml.xsl");
        File file = new File(fileName + ".xhtml");
        Result xhtmlOutput = new StreamResult(fileName + ".xhtml");

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xhtmlOutput);
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return file;
    }
}
