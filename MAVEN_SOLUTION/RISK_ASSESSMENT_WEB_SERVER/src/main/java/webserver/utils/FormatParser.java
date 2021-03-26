/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.utils;

import webserver.http.HTTPmessage;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author CarloS
 */
public abstract class FormatParser {

    private static final String XML_ROOT_ELEMENT = "xmlrootelement>";

    public static TextFormat getTextFormat(String format) {
        for (String fileExt : HTTPmessage.KNOWN_FILE_EXT.values()) {
            if (fileExt.equals(format)) {
                String temp[] = format.split("/");
                return TextFormat.valueOf(temp[1].toUpperCase());
            }
        }
        return TextFormat.NOTYPE;
    }

    /**
     * Converte um objeto de um tipo para outro
     *
     * @param fromFormat
     * @param toFormat
     * @param text
     * @return
     */
    public static String convert(TextFormat fromFormat, TextFormat toFormat, String text) {
        switch (toFormat) {
            case JSON:
                return convertToJSON(fromFormat, text);
            case XML:
                return convertToXML(fromFormat, text);
            default:
                throw new IllegalArgumentException("Conversion to " + toFormat.name() + " not supported");
        }
    }

    private static String convertToJSON(TextFormat fromFormat, String text) {
        switch (fromFormat) {
            case JSON:
                return new JSONObject(text).toString(2);
            case XML:
                return XML.toJSONObject(text).toString(2);
            default:
                throw new IllegalArgumentException("Conversion from " + fromFormat.name() + " to JSON not supported");
        }
    }

    private static String convertToXML(TextFormat fromFormat, String text) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            switch (fromFormat) {
                case JSON:
                    return fromDocToString(builder.parse(new InputSource(new StringReader("<" + XML_ROOT_ELEMENT + XML.toString(new JSONObject(text)) + "</" + XML_ROOT_ELEMENT))));
                case XML:
                    return fromDocToString(builder.parse(new InputSource(new StringReader("<" + XML_ROOT_ELEMENT + text + "</" + XML_ROOT_ELEMENT))));
                default:
                    throw new IllegalArgumentException("Conversion from " + fromFormat.name() + " to XML not supported");
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
            return null;
        }
    }

    private static String fromDocToString(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(domSource, result);
        return writer.toString();
    }
}
