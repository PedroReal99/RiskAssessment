/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.utils;

import core.domain.Case.CaseI;
import core.domain.Insurance.ClassificationTables.ClassificationColumn;
import core.domain.Insurance.ClassificationTables.ClassificationTable;
import core.domain.Insurance.ClassificationTables.TableLine;
import core.domain.Insurance.InsuranceObject;
import core.domain.RiskFactors.RiskFactor;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author CarloS
 */
public abstract class CaseParser {

    /**
     * Parses a Case to a xml
     *
     * @param casei
     * @return
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static String caseToXml(CaseI casei) throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element caseRoot = document.createElement("case");
        document.appendChild(caseRoot);

        caseRoot.appendChild(createTextNode(document, "code", casei.obtainCode()));
        caseRoot.appendChild(createTextNode(document, "comment",casei.getComment()));

        Element insurances = document.createElement("insurances");
        caseRoot.appendChild(insurances);
        Element history=document.createElement("history");
        for(String s:casei.history()){
            history.appendChild(createTextNode(document, "entry", s));
        }
        caseRoot.appendChild(history);
        
        for (InsuranceObject object : casei.getAssociatedInsuranceObjects()) {
            insurances.appendChild(createInsuranceNode(document, object));
        }

        return doToXmlString(document);
    }

    private static String doToXmlString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Element createTextNode(Document doc, String name, String text) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(text));
        return node;
    }

    private static Element createInsuranceNode(Document doc, InsuranceObject object) {
        Element node = doc.createElement("insuranceobject");
        node.appendChild(createTextNode(doc, "objectname", object.obtainName()));
        node.appendChild(createTextNode(doc, "location", object.obtainLocation()));
        node.appendChild(createTextNode(doc, "riskindex", object.obtainRiskIndex()));
        node.appendChild(createElementList(doc, "details", "detail", Arrays.asList((Object[]) object
                .obtainDetails().replaceAll("\\[|\\]", "").split(","))));
        node.appendChild(createClassificationNode(doc, object.getClassification()));
        return node;
    }

    private static Element createClassificationNode(Document doc, ClassificationTable classification) {
        Element node = doc.createElement("classificationtable");
        for (TableLine line : classification.obtainMatrix()) {
            node.appendChild(createCoverageClassNode(doc, line));
        }
        return node;
    }

    private static Element createCoverageClassNode(Document doc, TableLine line) {
        Element node = doc.createElement("coverageclass");
        node.appendChild(createTextNode(doc, "coveragename", line.obtainCoverage().obtainName()));
        Element sur = doc.createElement("surroundings");
        node.appendChild(sur);
        Map<RiskFactor, ClassificationColumn> map = line.obtainColumns();

        for (Element el : createSurroundingNodes(doc, map)) {
            sur.appendChild(el);
        }

        return node;
    }

    private static Set<Element> createSurroundingNodes(Document doc, Map<RiskFactor, ClassificationColumn> map) {
        Map<String, Set<Element>> surroundings = new HashMap<>();
        Set<Element> set;
        for (RiskFactor risk : map.keySet()) {
            if ((set = surroundings.get(risk.obtainSTName())) == null) {
                set = new HashSet<>();
                surroundings.put(risk.obtainSTName(), set);
            }
            set.add(createRiskFactorNode(doc, map, risk));
        }
        set = new HashSet<>();
        for (String s : surroundings.keySet()) {
            Element node = doc.createElement("surroundingtype");
            node.appendChild(createTextNode(doc, "sname", s));
            Element risks = doc.createElement("riskfactors");
            node.appendChild(risks);
            for (Element el : surroundings.get(s)) {
                risks.appendChild(el);
            }
            set.add(node);
        }

        return set;
    }

    private static Element createRiskFactorNode(Document doc, Map<RiskFactor, ClassificationColumn> map, RiskFactor risk) {
        Element node = doc.createElement("riskfactor");
        node.appendChild(createTextNode(doc, "metric", risk.obtainMetrics()));
        node.appendChild(createTextNode(doc, "classification", map.get(risk).getClassification().toString()));

        Element surroundings = doc.createElement("relevantsurroundings");
        for (String s : map.get(risk).getRelevantSurroundings()) {
            surroundings.appendChild(createTextNode(doc, "slocation", s));
        }
        node.appendChild(surroundings);
        return node;
    }

    private static Element createElementList(Document doc, String nodeName, String elementName, Iterable<Object> elements) {
        Element root = doc.createElement(nodeName);
        for (Object ob : elements) {
            root.appendChild(createTextNode(doc, elementName, ob.toString()));
        }
        return root;
    }

    /**
     * Parses a xml to a case
     *
     * @param xml
     * @return
     */
    public static CaseI xmlToCase(String xml) {
        return null;
    }

    /**
     *
     * @param xml
     * @return
     */
    public static String xmlToXhtml(String xml) {
        Source xmlInput = new StreamSource(new StringReader(xml));
        Source xsl = new StreamSource(CaseParser.class.getClassLoader().getResourceAsStream("AR04.xsl"));
        StringWriter xhtml=new StringWriter();
        Result xhtmlOutput = new StreamResult(xhtml);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xhtmlOutput);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xhtml.toString();
    }
}
