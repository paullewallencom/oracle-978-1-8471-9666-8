package xmlparser;


import oracle.xml.jaxp.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;


public class SAXParserApp extends DefaultHandler {


    public void parseXMLDocument() {
        try {
            JXSAXParserFactory factory =
                (JXSAXParserFactory)JXSAXParserFactory.newInstance();
            JXSAXParser saxParser = (JXSAXParser)factory.newSAXParser();
            InputStream input = new FileInputStream(new File("catalog.xml"));
            saxParser.parse(input, this);
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
    public void startDocument() throws SAXException {
        System.out.println("SAX Event : Start Document");
    }
    public void endDocument() throws SAXException {
        System.out.println("SAX Event : End Document");
    }
    public void startElement(java.lang.String namespaceURI,
                             java.lang.String localName,
                             java.lang.String qName,
                             Attributes atts) throws SAXException {
        System.out.println("SAX Event : Start Element");
        System.out.println("Element QName:" + qName);
        System.out.println("Element Local Name:" + localName);
        System.out.println("Element Namespace URI:" + namespaceURI);

        for (int i = 0; i < atts.getLength(); i++) {
            System.out.println("Attribute QName:" + atts.getQName(i));
            System.out.println("Attribute Local Name:" + atts.getLocalName(i));
            System.out.println("Attribute Namespace URI:" + atts.getURI(i));
            System.out.println("Attribute Value:" + atts.getValue(i));
        }
    }
    public void endElement(java.lang.String namespaceURI,
                           java.lang.String localName,
                           java.lang.String qName) throws SAXException {
        System.out.println("SAX Event : End Element");
        System.out.println("Element QName:" + qName);
    }
    public void characters(char[] ch, int start,
                           int length) throws SAXException {
        System.out.println("SAX Event : Text");

        String text = new String(ch, start, length).trim();
        if (text.length() > 0) {
            System.out.println("Text:" + text);
        }
    }
    public void error(SAXParseException e) throws SAXException {
        System.err.println("Error:" + e.getMessage());
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.err.println("Fatal Error:" + e.getMessage());
    }
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Warning:" + e.getMessage());
    }
    public static void main(String[] argv) {
        SAXParserApp saxParser = new SAXParserApp();
        saxParser.parseXMLDocument();
    }
}

