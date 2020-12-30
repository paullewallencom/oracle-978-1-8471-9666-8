package xmlparser;

import java.io.*;
import oracle.xml.jaxp.*;
import oracle.xml.parser.v2.*;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class DOMParserApp {

    public void parseXMLDocument() {
        try {
            JXDocumentBuilderFactory factory =
                (JXDocumentBuilderFactory)JXDocumentBuilderFactory.newInstance();
            factory.setAttribute(JXDocumentBuilderFactory.ERROR_STREAM,
                                 new FileOutputStream(new File("c:/output/errorStream.txt")));
            factory.setAttribute(JXDocumentBuilderFactory.SHOW_WARNINGS,
                                 Boolean.TRUE);
            JXDocumentBuilder documentBuilder =
                (JXDocumentBuilder)factory.newDocumentBuilder();
            InputStream input = new FileInputStream(new File("catalog.xml"));
            XMLDocument xmlDocument =
                (XMLDocument)(documentBuilder.parse(input));


            System.out.println("Encoding: " + xmlDocument.getEncoding());
            System.out.println("Version: " + xmlDocument.getVersion());
            NodeList namespaceNodeList =
                xmlDocument.getElementsByTagNameNS("http://xdk.com/catalog/journal",
                                                   "title");
            for (int i = 0; i < namespaceNodeList.getLength(); i++) {
                XMLElement namespaceElement =
                    (XMLElement)namespaceNodeList.item(i);
                System.out.println("Namespace Prefix: " +
                                   namespaceElement.getNamespaceURI());
                System.out.println("Namespace URI: " +
                                   namespaceElement.getPrefix());
                System.out.println("Element Name: " +
                                   namespaceElement.getTagName());
                System.out.println("Element text:  " +
                                   namespaceElement.getFirstChild().getNodeValue());
            }


            XMLElement rootElement =
                (XMLElement)(xmlDocument.getDocumentElement());
            System.out.println("Root Element is: " + rootElement.getTagName());
            if (rootElement.hasChildNodes()) {
                NodeList nodeList = rootElement.getChildNodes();
                iterateNodeList(rootElement, nodeList);
            }
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        }
    }


    public void iterateNodeList(Element elem, NodeList nodeList) {
        if (nodeList.getLength() > 1) {
            System.out.println("Element " + elem.getTagName() +
                               " has sub-elements\n");
        }


        for (int i = 0; i < nodeList.getLength(); i++) {
            XMLNode node = (XMLNode)nodeList.item(i);


            if (node.getNodeType() == XMLNode.ELEMENT_NODE) {
                XMLElement element = (XMLElement)node;
                System.out.println("Sub-element of " + elem.getNodeName());
                System.out.println("Element Tag Name:" + element.getTagName());
                System.out.println("Element text:  " +
                                   element.getFirstChild().getNodeValue());


                if (element.hasAttributes()) {
                    System.out.println("Element has attributes\n");
                    NamedNodeMap attributes = element.getAttributes();
                    for (int j = 0; j < attributes.getLength(); j++) {
                        XMLAttr attribute = (XMLAttr)attributes.item(j);
                        System.out.println("Attribute: " +
                                           attribute.getName() +
                                           " with value " +
                                           attribute.getValue());
                    }
                }

                if (element.hasChildNodes()) {
                    iterateNodeList(element, element.getChildNodes());
                }
            }
        }
    }

    public static void main(String[] argv) {
        DOMParserApp domParser = new DOMParserApp();
        domParser.parseXMLDocument();
    }
}

