package xmlparser;

import oracle.xml.jaxp.*;
import oracle.xml.parser.v2.*;
import java.io.*;
import org.w3c.dom.DOMException;
import javax.xml.parsers.ParserConfigurationException;


public class CreateXMLDocument {


    public void createXMLDocument() {
        try {


            JXDocumentBuilderFactory factory =
                (JXDocumentBuilderFactory)JXDocumentBuilderFactory.newInstance();
            JXDocumentBuilder documentBuilder =
                (JXDocumentBuilder)factory.newDocumentBuilder();
            XMLDocument xmlDocument =
                (XMLDocument)documentBuilder.newDocument();
            xmlDocument.setVersion("1.0");
            xmlDocument.setEncoding("UTF-8");


            XMLElement catalogElement =
                (XMLElement)(xmlDocument.createElement("catalog"));
            xmlDocument.appendChild(catalogElement);
            XMLElement journalElement =
                (XMLElement)(xmlDocument.createElementNS("http://xdk.com/catalog/journal",
                                                         "journal:journal"));
            catalogElement.appendChild(journalElement);


            journalElement.setAttributeNS("http://xdk.com/catalog/journal",
                                          "journal:title", "Oracle Magazine");
            journalElement.setAttributeNS("http://xdk.com/catalog/journal",
                                          "journal:publisher",
                                          "Oracle Publishing");
            journalElement.setAttributeNS("http://xdk.com/catalog/journal",
                                          "journal:edition",
                                          "March-April 2008");


            XMLElement articleElement =
                (XMLElement)(xmlDocument.createElementNS("http://xdk.com/catalog/journal",
                                                         "journal:article"));
            journalElement.appendChild(articleElement);

            articleElement.setAttributeNS("http://xdk.com/catalog/journal",
                                          "journal:section",
                                          "Oracle Developer");

            XMLElement titleElement =
                (XMLElement)(xmlDocument.createElementNS("http://xdk.com/catalog/journal",
                                                         "journal:title"));

            articleElement.appendChild(titleElement);

            XMLText title =
                (XMLText)xmlDocument.createTextNode(" Declarative Data Filtering");
            titleElement.appendChild(title);

            XMLElement authorElement =
                (XMLElement)(xmlDocument.createElementNS("http://xdk.com/catalog/journal",
                                                         "journal:author"));

            articleElement.appendChild(authorElement);

            XMLText author =
                (XMLText)xmlDocument.createTextNode("Steve Muench");
            authorElement.appendChild(author);

            journalElement =
                    (XMLElement)(xmlDocument.createElement("journal"));

            catalogElement.appendChild(journalElement);

            journalElement.setAttribute("title", "Oracle Magazine");
            journalElement.setAttribute("publisher", "Oracle Publishing");
            journalElement.setAttribute("edition", " September-October 2008");

            articleElement =
                    (XMLElement)(xmlDocument.createElement("article"));

            journalElement.appendChild(articleElement);

            articleElement.setAttribute("section", "FEATURES");

            titleElement = (XMLElement)(xmlDocument.createElement("title"));

            articleElement.appendChild(titleElement);

            title = (XMLText)xmlDocument.createTextNode("Share 2.0");
            titleElement.appendChild(title);

            authorElement = (XMLElement)(xmlDocument.createElement("author"));
            articleElement.appendChild(authorElement);

            author = (XMLText)xmlDocument.createTextNode("Alan Joch");
            authorElement.appendChild(author);


            OutputStream output =
                new FileOutputStream(new File("catalog.xml"));
            XMLPrintDriver xmlPrintDriver =
                new XMLPrintDriver(new PrintWriter(output));
            xmlPrintDriver.printDocument(xmlDocument);
            xmlPrintDriver.flush();
            xmlPrintDriver.close();
        } catch (DOMException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] argv) {
        CreateXMLDocument createXMLDocument = new CreateXMLDocument();
        createXMLDocument.createXMLDocument();
    }
}


