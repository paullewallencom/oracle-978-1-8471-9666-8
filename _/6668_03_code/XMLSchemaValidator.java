package schemavalidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import oracle.xml.schemavalidator.XSDValidator;
import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.XMLError;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;


public class XMLSchemaValidator {


    public void validateXMLDocument(InputStream input) {

        try {
            XSDValidator xsdValidator = new XSDValidator();
            XMLSchema schema = getXMLSchema();
            xsdValidator.setSchema(schema);
            CustomErrorHandler errorHandler = new CustomErrorHandler();
            XMLError xmlError = new XMLError();
            xmlError.setErrorHandler(errorHandler);
            xsdValidator.setError(xmlError);
            xsdValidator.validate(input);


            if (errorHandler.hasValidationError == true)
                System.err.println("XML Document has  Validation  Error:" +
                                   errorHandler.saxParseException.getMessage());
            else
                System.out.println("XML Document validates with XML Schema");

        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("SAXException " + e.getMessage());
        } catch (XSDException e) {
            System.err.println("XSDException " + e.getMessage());
        }
    }


    private class CustomErrorHandler extends DefaultHandler {
        protected boolean hasValidationError = false;
        protected SAXParseException saxParseException = null;

        public void error(SAXParseException exception) {
            hasValidationError = true;
            saxParseException = exception;
        }

        public void fatalError(SAXParseException exception) {
            hasValidationError = true;
            saxParseException = exception;
        }

        public void warning(SAXParseException exception) {
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
            XMLSchemaValidator validator = new XMLSchemaValidator();
            validator.validateXMLDocument(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }

    }
}
