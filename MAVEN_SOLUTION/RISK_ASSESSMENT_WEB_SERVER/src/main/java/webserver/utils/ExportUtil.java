/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

//import static com.oracle.webservices.internal.api.EnvelopeStyle.Style.XML;
import core.domain.Surrounding.Surrounding;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.json.JSONObject;
import org.xml.sax.*;
import org.w3c.dom.*;
//import com.oracle.webservices.internal.api.EnvelopeStyle.Style.XML;
import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.json.XML;

/**
 *
 * @author Pedro
 */
public class ExportUtil {

    public void exportToXHTML(String body,String xmlfile,String xsdfile,String xhtmlfile,String xslfile, String func,String str1) {
        File file = transformIntoXML(body,xmlfile , func, str1);
        if(validateXML(file,xsdfile)){
            transformXMLToXHTML(file,xslfile,xhtmlfile);
        }
    }

    public void exportToJSon(String body, String xmlfile , String jsonfile, String func, String str1) throws IOException {
        File xmlFile = transformIntoXML(body,xmlfile ,func,str1);
        transformXMLToJSON(xmlFile,jsonfile);
    }

    public File transformXMLToXHTML(File file , String xslfile, String xhtmlfile) {
        Source xmlInput = new StreamSource(file);
        Source xsl = new StreamSource(new File(getClass().getClassLoader().getResource(xslfile).getFile()));
        File xhtml = new File(xhtmlfile);
        Result xhtmlOutput = new StreamResult(xhtml);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xhtmlOutput);
        } catch (TransformerException | NullPointerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return xhtml;
    }

    public File transformIntoXML(String body, String xmlfile , String func, String str1) {
        File file = new File(xmlfile);
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(func);
            doc.appendChild(rootElement);

            Element result = doc.createElement(str1);
            result.appendChild(doc.createTextNode(body));
            rootElement.appendChild(result);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result2 = new StreamResult(file);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result2);

            //System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        return file;
    }


    public File transformXMLToJSON(File file, String jsonfile) throws IOException {
        try {
            InputStream inputStream = new FileInputStream(file);
            StringBuilder builder = new StringBuilder();
            int ptr = 0;
            while ((ptr = inputStream.read()) != -1) {
                builder.append((char) ptr);
            }

            String xml = builder.toString();
            JSONObject jsonObj = XML.toJSONObject(xml);
            FileWriter fileWriter
                    = new FileWriter(jsonfile);

            BufferedWriter bufferedWriter
                    = new BufferedWriter(fileWriter);

            for (int i = 0; i < jsonObj.toString().split(",").length; i++) {
                System.out.println(jsonObj.toString().split(",")[i]);
                bufferedWriter.write(jsonObj.toString().split(",")[i]);
                bufferedWriter.write("\n");
            }

            bufferedWriter.close();
        }  

          catch (IOException ex) {
            System.out.println(
                    "Error writing to file '"
                    + jsonfile + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(jsonfile);
    }
    
    public boolean validateXML(File file , String xsdfile) {
        File schemaFile = new File(getClass().getClassLoader().getResource(xsdfile).getFile());
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

}
