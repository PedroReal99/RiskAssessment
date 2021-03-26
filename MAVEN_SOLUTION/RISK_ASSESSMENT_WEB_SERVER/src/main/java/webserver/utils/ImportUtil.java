/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Pedro
 */
public class ImportUtil {

    public File transformIntoXML(String body, String pathNameXML) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        File a = new File(pathNameXML);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            // Use String reader
            Document document = builder.parse(new InputSource(
                    new StringReader(body)));

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            Source src = new DOMSource(document);
            //a = new File("S04.xml");
            Result dest = new StreamResult(a);
            aTransformer.transform(src, dest);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;
    }

    public boolean validateXML(File file, String pathName) {
        File schemaFile = new File(pathName); // etc.
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

    public List<String> getInformationFromXML(File file) {
        List<String> list = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Versions");
            list.add(doc.getElementsByTagName("CaseCode").item(0).getTextContent());
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    list.add(eElement.getElementsByTagName("Version").item(0).getTextContent());
                    list.add(eElement.getElementsByTagName("Version").item(1).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public File transformIntoJson(String body) throws IOException {
        File file = new File("SE04.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(body);

        writer.close();
        return file;
    }

    public List<String> getInformationFromJSON(File file) throws FileNotFoundException, IOException, ParseException {
        JSONParser jp = new JSONParser();
        List<String> list = new LinkedList<>();
        JSONObject jObj = (JSONObject) jp.parse(new FileReader(file.getName()));
        list.add((String) jObj.get("CaseCode"));
        JSONArray ja2 = (JSONArray) jObj.get("Versions");
        for (int k = 0; k < ja2.size(); k++) {
            JSONObject j3 = (JSONObject) ja2.get(k);
            list.add((String) j3.get("Version"));
        }
        return list;
    }
    
    
//        public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
//        File a = new File("teste.json");
//        List<String> l = getInformationFromJSON(a);
////        List<String> b = getInformationFromXML(a);
//       // validateXML(a);
//       
//    }

}
