/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author khai
 */
public class XMLUlti implements Serializable {

    public static Document parseFileTODOMTREE(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFilePath);
        return doc;
    }

    public static XPath getXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        return xpath;
    }

    public static void tranformXMLtoFile(Node node, String xmlFilePath) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        Source src = new DOMSource(node);
        File file = new File(xmlFilePath);
        Result result = new StreamResult(file);
        transformer.transform(src, result);
    }

    public static Element createDOMElement(Document doc, String elementName,
            String textContent, Map<String, String> attribute) {
        if (doc == null) {
            return null;
        }
        if (elementName == null) {
            return null;
        }
        if (elementName.trim().length() == 0) {
            return null;
        }
        Element result = doc.createElement(elementName);
        if (textContent != null) {
            result.setTextContent(textContent);

        }
        if (attribute != null) {
            if(!attribute.isEmpty()){
                for(Map.Entry<String, String> entry: attribute.entrySet()){
                    result.setAttribute(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;
    }

    public static void Transfer(String xsltPath, String xmlPath, String htmlPath) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            StreamSource xsltFile = new StreamSource(xsltPath);
            Templates template = tf.newTemplates(xsltFile);
            Transformer trans = template.newTransformer();
            StreamSource xmlFile = new StreamSource(xmlPath);
            StreamResult htmlFile = new StreamResult(htmlPath);
            trans.transform(xmlFile, htmlFile);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
