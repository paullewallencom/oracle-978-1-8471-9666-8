package dom3validation;

import org.w3c.dom.validation.*;
import org.w3c.dom.ls.*;

import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.*;

import org.w3c.dom.*;

import java.io.*;

import java.net.*;


public class DOM3Validation {


    public void validate() {

        try {

            XMLDOMImplementation impl = new XMLDOMImplementation();
            LSParser parser =
                impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS,
                                    "http://www.w3.org/2001/XMLSchema");

            XMLDocument document =
                (XMLDocument)(parser.parseURI("file://C:/DOM3Validation/catalog.xml"));
            XSDBuilder builder = new XSDBuilder();
            URL url = new URL("file://C:/DOM3Validation/catalog.xsd");
            XMLSchema schemadoc = builder.build(url);
            document.setSchema(schemadoc);


            NameList elementList = document.getDefinedElements(null);
            for (int i = 0; i < elementList.getLength(); i++)
                System.out.println("Element at index " + i + " is " +
                                   elementList.getName(i));

            XMLElement catalogElement =
                (XMLElement)(document.getDocumentElement());

            NameList attrRequired = catalogElement.getRequiredAttributes();
            System.out.println("The required attributes of the catalog element are: ");
            for (int i = 0; i < attrRequired.getLength(); i++)
                System.out.println(attrRequired.getName(i) + " ");

            NameList attr = catalogElement.getAllowedAttributes();
            System.out.println("The allowed attributes of the catalog element are: ");
            for (int i = 0; i < attr.getLength(); i++)
                System.out.println(attr.getName(i) + " ");


            short attrSet = catalogElement.canSetAttribute("section", "SQL");
            if (attrSet == NodeEditVAL.VAL_TRUE)
                System.out.println("Attribute Addition is VALID_TRUE");
            if (attrSet == NodeEditVAL.VAL_FALSE)
                System.out.println("Attribute Addition is VALID_FALSE");
            if (attrSet == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Attribute Addition is VALID_UNKNOWN");

            short attrRemove = catalogElement.canRemoveAttribute("title");

            if (attrRemove == NodeEditVAL.VAL_TRUE)
                System.out.println("Attribute Removal is VALID_TRUE");
            if (attrRemove == NodeEditVAL.VAL_FALSE)
                System.out.println("Attribute Removal is VALID_FALSE");
            if (attrRemove == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Attribute Removal is VALID_UNKNOWN");

            attrRemove = catalogElement.canRemoveAttribute("publisher");

            if (attrRemove == NodeEditVAL.VAL_TRUE)
                System.out.println("Attribute Removal is VALID_TRUE");
            if (attrRemove == NodeEditVAL.VAL_FALSE)
                System.out.println("Attribute Removal is VALID_FALSE");
            if (attrRemove == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Attribute Removal is VALID_UNKNOWN");


            XMLElement journalNode =
                (XMLElement)(document.selectSingleNode("catalog/journal"));
            short elementRemove = catalogElement.canRemoveChild(journalNode);

            if (elementRemove == NodeEditVAL.VAL_TRUE)
                System.out.println("Element Removal is VALID_TRUE");
            if (elementRemove == NodeEditVAL.VAL_FALSE)
                System.out.println("Element Removal is VALID_FALSE");
            if (elementRemove == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Element Removal is VALID_UNKNOWN");

            attrRequired = journalNode.getRequiredAttributes();
            System.out.println("The required attributes of the journal element are: ");
            for (int i = 0; i < attrRequired.getLength(); i++)
                System.out.println(attrRequired.getName(i) + " ");

            attr = journalNode.getAllowedAttributes();
            System.out.println("The allowed attributes of the journal element are: ");
            for (int i = 0; i < attr.getLength(); i++)
                System.out.println(attr.getName(i) + " ");

            short setAttr =
                journalNode.canSetAttribute("date", "March-April 2008");
            if (setAttr == NodeEditVAL.VAL_TRUE) {
                journalNode.setAttribute("date", "March-April 2008");
                System.out.println("Attribute Addition is VALID_TRUE");
            }

            if (attrSet == NodeEditVAL.VAL_FALSE)
                System.out.println("Attribute Addition is VALID_FALSE");
            if (attrSet == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Attribute Addition is VALID_UNKNOWN");

            XMLElement articleNode =
                (XMLElement)(document.selectSingleNode("catalog/journal/article"));
            attrRequired = articleNode.getRequiredAttributes();

            System.out.println("The required attributes of the article element are: ");
            for (int i = 0; i < attrRequired.getLength(); i++)
                System.out.println(attrRequired.getName(i) + " ");

            attr = articleNode.getAllowedAttributes();
            System.out.println("The allowed attributes of the article element are: ");
            for (int i = 0; i < attr.getLength(); i++)
                System.out.println(attr.getName(i) + " ");

            setAttr =
                    articleNode.canSetAttribute("section", "ORACLE DEVELOPER");
            if (setAttr == NodeEditVAL.VAL_TRUE)
                articleNode.setAttribute("section", "ORACLE DEVELOPER");

            setAttr = articleNode.canSetAttribute("section", "XSLT");
            if (attrSet == NodeEditVAL.VAL_TRUE)
                System.out.println("Attribute Addition is VALID_TRUE");
            if (attrSet == NodeEditVAL.VAL_FALSE)
                System.out.println("Attribute Addition is VALID_FALSE");
            if (attrSet == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Attribute Addition is VALID_UNKNOWN");

            short contentType = articleNode.getContentType();
            if (contentType == ElementEditVAL.VAL_ANY_CONTENTTYPE)
                System.out.println("Content type of article element is  VAL_ANY_CONTENTTYPE");
            if (contentType == ElementEditVAL.VAL_ELEMENTS_CONTENTTYPE)
                System.out.println("Content type of article element is  VAL_ELEMENTS_CONTENTTYPE");
            if (contentType == ElementEditVAL.VAL_SIMPLE_CONTENTTYPE)
                System.out.println("Content type of article element is  VAL_SIMPLE_CONTENTTYPE");
            if (contentType == ElementEditVAL.VAL_EMPTY_CONTENTTYPE)
                System.out.println("Content type of article element is   VAL_EMPTY_CONTENTTYPE");
            if (contentType == ElementEditVAL.VAL_MIXED_CONTENTTYPE)
                System.out.println("Content type of article element is  VAL_MIXED_CONTENTTYPE");

            elementList = articleNode.getAllowedChildren();

            System.out.println("Elements which may be specified in an article element are: ");
            for (int i = 0; i < elementList.getLength(); i++)
                System.out.println(elementList.getName(i) + " ");

            XMLElement titleElement =
                (XMLElement)document.createElement("title");
            short editVal = articleNode.canAppendChild(titleElement);

            if (editVal == NodeEditVAL.VAL_TRUE) {
                System.out.println("Element addition VALID_TRUE");
                articleNode.appendChild(titleElement);
            }

            if (editVal == NodeEditVAL.VAL_FALSE)
                System.out.println("Element addition is VALID_FALSE");
            if (editVal == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Element addition is VALID_UNKNOWN");


            XMLElement authorElement =
                (XMLElement)document.createElement("author");
            editVal = articleNode.canAppendChild(authorElement);

            if (editVal == NodeEditVAL.VAL_TRUE) {
                System.out.println("Element addition VALID_TRUE");
                articleNode.appendChild(authorElement);
            }

            if (editVal == NodeEditVAL.VAL_FALSE)
                System.out.println("Element addition is VALID_FALSE");
            if (editVal == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Element addition is VALID_UNKNOWN");


            XMLText title =
                (XMLText)document.createTextNode("Declarative Data Filtering");
            titleElement.appendChild(title);
            XMLText author = (XMLText)document.createTextNode("Steve Muench");
            authorElement.appendChild(author);

            short nodeValid =
                journalNode.nodeValidity(ElementEditVAL.VAL_SCHEMA);
            if (nodeValid == NodeEditVAL.VAL_TRUE)
                System.out.println("Node Valid VALID_TRUE");
            if (nodeValid == NodeEditVAL.VAL_FALSE)
                System.out.println("Node Valid VALID_FALSE");
            if (nodeValid == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Node Valid VALID_UNKNOWN");

            short elementDefined = catalogElement.isElementDefined("title");
            if (elementDefined == NodeEditVAL.VAL_TRUE)
                System.out.println("Element is defined VALID_TRUE");
            if (elementDefined == NodeEditVAL.VAL_FALSE)
                System.out.println("Element is defined VALID_FALSE");
            if (elementDefined == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Element is defined VALID_UNKNOWN");

            editVal = catalogElement.canAppendChild(titleElement);

            if (editVal == NodeEditVAL.VAL_TRUE)
                System.out.println("Element addition VALID_TRUE");
            if (editVal == NodeEditVAL.VAL_FALSE)
                System.out.println("Element addition is VALID_FALSE");
            if (editVal == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Element addition is VALID_UNKNOWN");

            elementList = catalogElement.getAllowedChildren();

            System.out.println("Elements which may be specified in the catalog element are: ");
            for (int i = 0; i < elementList.getLength(); i++)
                System.out.println(elementList.getName(i) + " ");

            XMLElement journalElement =
                (XMLElement)document.createElement("journal");
            journalElement.setAttribute("date", "May-June 2008");

            XMLElement articleElement =
                (XMLElement)document.createElement("article");
            journalElement.appendChild(articleElement);
            articleElement.setAttribute("section", "ORACLE DEVELOPER");
            titleElement = (XMLElement)document.createElement("title");
            articleElement.appendChild(titleElement);
            authorElement = (XMLElement)document.createElement("author");
            articleElement.appendChild(authorElement);

            title =
                    (XMLText)document.createTextNode("On the PGA and Indexing Collections");
            titleElement.appendChild(title);
            author = (XMLText)document.createTextNode("Steven Feuerstein");
            authorElement.appendChild(author);

            editVal = catalogElement.canAppendChild(journalElement);

            if (editVal == NodeEditVAL.VAL_TRUE) {
                System.out.println("Element addition VALID_TRUE");
                catalogElement.appendChild(journalElement);

            }

            if (editVal == NodeEditVAL.VAL_FALSE)
                System.out.println("Element addition is VALID_FALSE");
            if (editVal == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Element addition is VALID_UNKNOWN");


            short valid = document.validateDocument();
            if (valid == NodeEditVAL.VAL_TRUE)
                System.out.println("Document is VALID_TRUE");
            if (valid == NodeEditVAL.VAL_FALSE)
                System.out.println("Document is VALID_FALSE");
            if (valid == NodeEditVAL.VAL_UNKNOWN)
                System.out.println("Document is VALID_UNKNOWN");


            LSSerializer domWriter = impl.createLSSerializer();
            LSOutput output = impl.createLSOutput();
            OutputStream outputStream =
                new FileOutputStream(new File("catalog.xml"));
            output.setByteStream(outputStream);
            output.setEncoding("UTF-8");
            domWriter.write(document, output);


        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        } catch (XSDException e) {
            System.err.println(e.getMessage());
        } catch (XSLException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args) {

        DOM3Validation dom3Validation = new DOM3Validation();
        dom3Validation.validate();


    }
}

