/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.utils;

import core.domain.location.GPSLocation;
import core.domain.location.PostLocation;
import core.domain.Surrounding.Surrounding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

/**
 *
 * @author Ricardo Branco
 */
public class ExportUtil {

    private Iterable<Surrounding> surroundings;
    private String XHTMLfilename;
    private static final String xml_file = "auxiliar.xml";

    public ExportUtil(Iterable<Surrounding> surroundings, String XHTMLfilename) {
        this.surroundings = surroundings;
        this.XHTMLfilename = XHTMLfilename;
    }
    
    public void exportToXHTML() {
        saveToXML();
        transformXMLToXHTML();
    }

    private void transformXMLToXHTML() {
        Source xmlInput = new StreamSource(xml_file);
        Source xsl = new StreamSource("xml2xhtml.xsl");
        Result xhtmlOutput = new StreamResult(XHTMLfilename);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xhtmlOutput);
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void saveToXML() {
        Document dom;
        Element elemSurrounding = null;
        Element elemType = null;
        Element elemName = null;
        Element elemLocation = null;
        Element elemGPSLocation = null;
        Element elemLat = null;
        Element elemLon = null;
        Element elemAlt = null;
        Element elemPostLocation = null;
        Element elemCountry = null;
        Element elemDistrict = null;
        Element elemRoad = null;
        Element elemNumber = null;
        Element elemPostCode = null;


        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();
            // create the root element
            Element rootEle = dom.createElement("surroundings");

            for (Surrounding surrounding : surroundings) {
                // create data elements and place them under root
                elemSurrounding = dom.createElement("surrounding");                
                rootEle.appendChild(elemSurrounding);
                
                elemName = dom.createElement("name");
                elemName.appendChild(dom.createTextNode(surrounding.obtainSName().toString()));
                elemSurrounding.appendChild(elemName);
                
                elemType = dom.createElement("type");  
                elemType.appendChild(dom.createTextNode(surrounding.obtainSTName().toString()));              
                elemSurrounding.appendChild(elemType);
                
                elemLocation = dom.createElement("location"); 
                elemSurrounding.appendChild(elemLocation);
                
                elemGPSLocation = dom.createElement("gpslocation");
                elemLocation.appendChild(elemGPSLocation);
                
                elemLat = dom.createElement("latitude");
                elemLat.appendChild(dom.createTextNode(String.valueOf((surrounding.obtainLocation()).getLatitude())));
                elemGPSLocation.appendChild(elemLat);
                
                elemLon = dom.createElement("longitude");
                elemLon.appendChild(dom.createTextNode(String.valueOf((surrounding.obtainLocation()).getLongitude())));
                elemGPSLocation.appendChild(elemLon);
                
                elemAlt = dom.createElement("altitude");
                elemAlt.appendChild(dom.createTextNode(String.valueOf((surrounding.obtainLocation()).getAltitude())));
                elemGPSLocation.appendChild(elemAlt);
                
                elemPostLocation = dom.createElement("postlocation");
                elemLocation.appendChild(elemPostLocation);
                
                elemDistrict = dom.createElement("district");
                elemDistrict.appendChild(dom.createTextNode((surrounding.obtainLocation()).getDistrict()));
                elemPostLocation.appendChild(elemDistrict);
                
            }

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(xml_file)));

            } catch (TransformerException | IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }
}
