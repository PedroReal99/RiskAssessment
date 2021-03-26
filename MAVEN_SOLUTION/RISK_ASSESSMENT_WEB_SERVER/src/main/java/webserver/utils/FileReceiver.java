/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import webserver.controller.GetRiskAssessmentResultController;
import webserver.utils.TextFormat;
import webserver.utils.ImportUtil;
import webserver.controller.ComparisonRiskAssessmentController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Pedro
 */
public class FileReceiver {

    ImportUtil iu = new ImportUtil();
    ExportUtil eu = new ExportUtil();
    ComparisonRiskAssessmentController crac = new ComparisonRiskAssessmentController();
    GetRiskAssessmentResultController grac = new GetRiskAssessmentResultController();

    public File fileToMethods(String body, TextFormat type, String uc) throws IOException, FileNotFoundException, ParseException {
        List<String> list = new ArrayList<>();
        String pathNameSchema = "";
        String pathNameXML = "";
        String exportFile = "";
        String exportFileSchema = "";
        String xslFileName = "";
        String xhtmlFileName = "";
        String jsonFileName = "";
        if (uc.equalsIgnoreCase("se02")) {
            pathNameXML = "S02.xml";
            pathNameSchema = "SchemaSE02.xsd";
            exportFile = "SE02-Export.xml";
            exportFileSchema = "SchemaSE02Export.xsd";
            xslFileName = "SE02XML2XHTML.xsl";
            xhtmlFileName = "SE02XHTML.xhtml";
            jsonFileName = "SE02-Export.json";
        } else if (uc.equalsIgnoreCase("se04")) {
            pathNameXML = "S04.xml";
            pathNameSchema = "SchemaSE04.xsd";
            exportFile = "SE04-Export.xml";
            exportFileSchema = "SchemaSE04Export.xsd";
            xslFileName = "SE04XML2XHTML.xsl";
            xhtmlFileName = "SE04XHTML.xhtml";
            jsonFileName = "SE04-Export.json";
        }
        if (type.equals(type.XML)) {
            File file = iu.transformIntoXML(body, pathNameXML);
            iu.validateXML(file, pathNameSchema);
            list = iu.getInformationFromXML(file);
            String str = "";
            if (uc.equalsIgnoreCase("se02")) {
                str = grac.getIntegerRiskIndex(list.get(0), list.get(1));
            } else if (uc.equalsIgnoreCase("se04")) {
                str = crac.comparisonRiskAssessment(list.get(1), list.get(2), list.get(0));
            }
            File xml = eu.transformIntoXML(str, exportFile, uc, "Result");
            eu.validateXML(xml, exportFileSchema);
            File xhtml = eu.transformXMLToXHTML(file, xslFileName, xhtmlFileName);
            //Manda po server

            return xhtml;
        } else if (type.equals(type.JSON)) {
            File file = iu.transformIntoJson(body);
            list = iu.getInformationFromJSON(file);
            String str = crac.comparisonRiskAssessment(list.get(1), list.get(2), list.get(0));
            File xml = eu.transformIntoXML(str, exportFile, uc, "Result");
            eu.validateXML(xml, exportFileSchema);
            File json = eu.transformXMLToJSON(xml, jsonFileName);
            //Export po server

            return json;
        }
        return null;
    }
}
