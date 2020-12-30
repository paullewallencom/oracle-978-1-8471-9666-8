package schemavalidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.*;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;


public class SAXValidator {


    public void validateXMLDocument(InputSource input) {
        try {

            SAXParser saxParser = new SAXParser();
            saxParser.setValidationMode(XMLParser.SCHEMA_VALIDATION);
            XMLSchema schema = getXMLSchema();

            saxParser.setXMLSchema(schema);

            CustomErrorHandler errorHandler = new CustomErrorHandler();
            saxParser.setErrorHandler(errorHandler);
            saxParser.parse(input);

            if (errorHandler.hasValidationError == true) {
                System.err.println("XML Document has Validation Error:" +
                                   errorHandler.saxParseException.getMessage());
            } else {
                System.err.println("XML Document validates with XML Schema");
            }
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("SAXException " + e.getMessage());
        }
    }

    public XMLSchema getXMLSchema() {

        try {
            XSDBuilder builder = new XSDBuilder();
            InputStream inputStream =
                new FileInputStream(new File("catalog.xsd"));
            InputSource inputSource = new InputSource(inputStream);
            XMLSchema schema = builder.build(inputSource);
            return schema;
        } catch (XSDException e) {
            System.err.println("XSDException " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] argv) {
        try {
            InputStream inputStream =
                new FileInputStream(new File("catalog.xml"));
            InputSource inputSource = new InputSource(inputStream);
            SAXValidator validator = new SAXValidator();
            validator.validateXMLDocument(inputSource);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
    }
    private class CustomErrorHandler extends DefaultHandler {
        protected boolean hasValidationError = false;
        protected SAXParseException saxParseException = null;

        public void error(SAXParseException exception) throws SAXException {
            hasValidationError = true;
            saxParseException = exception;
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            hasValidationError = true;
            saxParseException = exception;
        }

        public void warning(SAXParseException exception) throws SAXException {
        }
    }
}
