package dom3ls;

import org.w3c.dom.ls.*;
import org.w3c.dom.*;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import oracle.xml.parser.v2.*;
import java.net.URL;
import java.io.*;


public class DOM3Builder implements EventListener {


    public void loadDocument() {
        try {


            DOMImplementationLS domImpl = new XMLDOMImplementation();
            LSParser parser =
                domImpl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS,
                                       null);
            XMLLSParser lsParser = (XMLLSParser)parser;
            lsParser.addEventListener("ls-load",
                                      new DOM3Builder(),
                                      true);


            Document document =
                lsParser.parseURI("file://C:/DOM3.0/catalog.xml");
            Node node =
                ((XMLDocument)(document)).selectSingleNode("/catalog/journal[@date='May-June 2008']");
            LSInput lsInput = domImpl.createLSInput();
            URL url = new URL("file://C:/DOM3.0/replace-node.xml");
            lsInput.setSystemId(url.toString());
            lsParser.parseWithContext(lsInput, node, LSParser.ACTION_REPLACE);
            System.out.println("XML Document Node has been replaced");


            XMLPrintDriver output =
                new XMLPrintDriver(new FileOutputStream(new File("catalog-modified.xml")));
            
            output.setEncoding("utf-8");
            output.printDocument((XMLDocument)document);
            output.close();
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        } catch (DOMException e) {
            System.err.println("DOMException " + e.getMessage());
        } catch (XSLException e) {
            System.err.println("XSLException " + e.getMessage());
        } 
    }


    public void handleEvent(Event event) {
        if (event instanceof LSLoadEvent) {
            LSLoadEvent loadEvent = (LSLoadEvent)event;
            Document document = loadEvent.getNewDocument();
            System.out.println("XML Document with root element " +
                               document.getDocumentElement().getTagName() +
                               " has been loaded.");
        }
    }


    public static void main(String[] args) {
        DOM3Builder builder = new DOM3Builder();
        builder.loadDocument();
    }
}
