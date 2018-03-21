package org.survey;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Ignore
public class XPathTest {
    private XPath xpath;
    private Document doc;

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        doc = builder.parse("src/test/resources/xpath-test.xhtml");
        xpath = XPathFactory.newInstance().newXPath();
    }

    @Test
    public void testXPath() throws XPathExpressionException, ParserConfigurationException {
        XPathExpression expr = xpath.compile("//tr[contains(td/text(),'another1')]");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        log.debug(xmlDocumentAsString(createDocument(nodes)));
    }

    @Test
    public void testXPath2() throws XPathExpressionException, ParserConfigurationException {
        XPathExpression expr = xpath.compile("//tr[contains(td/text(),'another1')]//td[contains(@id,'role')]");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        log.debug(xmlDocumentAsString(createDocument(nodes)));
    }

    @Test
    public void testXPath3() throws XPathExpressionException, ParserConfigurationException {
        XPathExpression expr = xpath.compile("//tr[contains(td/text(),'another1')]//td[contains(@id,'role')]/text()");
        log.debug(expr.evaluate(doc, XPathConstants.STRING).toString());
    }

    @Test
    public void testXPath4() throws XPathExpressionException, ParserConfigurationException {
        XPathExpression expr = xpath.compile("//tr[contains(td/text(),'another1')]");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        log.debug(xmlDocumentAsString(createDocument(nodes)));
    }

    @Test
    public void testXPath5() throws XPathExpressionException, ParserConfigurationException {
        XPathExpression expr = xpath.compile("//tr[contains(td/text(),'another1')]//input[contains(@id,'edit')]");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        log.debug(xmlDocumentAsString(createDocument(nodes)));
    }

    private Document createDocument(NodeList nodes) throws ParserConfigurationException {
        Document newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Node copyNode = newXmlDocument.importNode(node, true);
            newXmlDocument.appendChild(copyNode);
        }
        return newXmlDocument;
    }

    private String xmlDocumentAsString(Document document) {
        DOMImplementationLS domImplementationLS = (DOMImplementationLS) document.getImplementation();
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        return lsSerializer.writeToString(document);
    }
}
