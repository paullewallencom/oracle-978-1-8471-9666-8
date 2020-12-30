package trax;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import javax.xml.transform.TransformerException;
import oracle.xml.jaxp.JXSAXTransformerFactory;
import oracle.xml.jaxp.JXTransformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;


public class XSLTransform {

    public static void main(String[] argv) {
        File stylesheet = new File("catalog.xsl");
        File xmlFile = new File("catalog.xml");
        XSLTransform xsltTransform = new XSLTransform();
        xsltTransform.transformXML(xmlFile, stylesheet);
    }
    public void transformXML(File xmlFile, File xslFile) {
        try {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            JXSAXTransformerFactory tFactory =
                (JXSAXTransformerFactory)(JXSAXTransformerFactory.newInstance());
            CustomErrorListener errorListener = new CustomErrorListener();
            tFactory.setErrorListener(errorListener);

            StreamSource stylesource = new StreamSource(xslFile);
            JXTransformer transformer =
                (JXTransformer)tFactory.newTransformer(stylesource);
            CustomErrorListener errorListener2 = new CustomErrorListener();
            transformer.setErrorListener(errorListener2);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(document);
            StreamResult result =
                new StreamResult(new File("catalog-modified.xml"));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {

            System.err.println("TransformerConfigurationException   " +
                               e.getMessage());
            
        } catch (TransformerException e) {

            System.err.println("TransformerException   " + e.getMessage());

        } catch (SAXException e) {

            System.err.println("SAXException  " + e.getMessage());

        } catch (ParserConfigurationException e) {
            System.err.println("ParserConfigurationException  " +
                               e.getMessage());

        } catch (IOException e) {
            System.err.println("IOException  " + e.getMessage());
        }
    }

    private class CustomErrorListener implements ErrorListener

    {
        public void error(TransformerException exception)

        {
            System.err.println("TransformerException: " + exception);
        }

        public void fatalError(TransformerException exception)

        {
            System.err.println("TransformerException: " + exception);
        }

        public void warning(TransformerException exception)

        {
            System.err.println("TransformerException: " + exception);
        }
    }
}

