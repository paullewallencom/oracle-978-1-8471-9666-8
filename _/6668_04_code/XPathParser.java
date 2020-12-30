package xpath;

import oracle.xml.parser.v2.*;
import java.io.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;


public class XPathParser {
    public void parseDocument(File xmlDocument) {
        try {
            DOMParser domParser = new DOMParser();
            domParser.parse(new FileReader(xmlDocument));
            XMLDocument document = domParser.getDocument();
            CustomNSResolver resolver = new CustomNSResolver();
            resolver.resolveNamespacePrefix("journal");

            XMLNode titleNode =
                (XMLNode)document.selectSingleNode("/catalog/journal/article[@section='ORACLE DEVELOPER']/title");
            String title = titleNode.getFirstChild().getNodeValue();

            System.out.println("Title of first Article in Developer Section (with nodes not in any namespace) is " +
                               title);
            XMLNode authorNode =
                (XMLNode)document.selectSingleNode("/catalog/journal/article[title='Oracle Database 11g Redux']/author");
            String author = authorNode.getFirstChild().getNodeValue();

            System.out.println("Author of Oracle Database 11g Redux is " +
                               author);
            XMLNode sectionNode =
                (XMLNode)document.selectSingleNode("/catalog/journal[@date='March-April 2008']/article[2]/@section");
            String section = sectionNode.getNodeValue();
            System.out.println("Section of 2nd Article in Journal of date March-April 2008 is  " +
                               section);
            NodeList nodeList =
                document.selectNodes("/catalog/journal[@date='March-April 2008']/article/title");
            System.out.println("Article Titles published in journal of March-April 2008 are: ");

            for (int i = 0; i < nodeList.getLength(); i++) {

                titleNode = (XMLElement)nodeList.item(i);
                title = titleNode.getFirstChild().getNodeValue();
                System.out.println(title);
            }
            nodeList =
                    document.selectNodes("/catalog/journal[@date='March-April 2008']/article/@section");
            System.out.println("Articles in journal of March-April 2008 were published in Sections: ");
            for (int i = 0; i < nodeList.getLength(); i++) {

                sectionNode = (XMLNode)nodeList.item(i);
                section = sectionNode.getNodeValue();
                System.out.println(section + " ");
            }
            sectionNode =
                    (XMLNode)document.selectSingleNode("/catalog/journal:journal/journal:article/@journal:section",
                                                       resolver);
            section = sectionNode.getNodeValue();
            System.out.println("Section of first article in first journal (nodes being in journal namespace) is " +
                               section + " ");
            System.out.println("Titles for articles in journal of date November-December 2008 (journal, article, and date nodes being in journal namespace) are  ");
            nodeList =
                    document.selectNodes("/catalog/journal:journal[@journal:date='November-December 2008']/journal:article/title",
                                         resolver);
            for (int i = 0; i < nodeList.getLength(); i++) {

                titleNode = (XMLElement)nodeList.item(i);
                title = titleNode.getFirstChild().getNodeValue();
                System.out.println(title);
            }

        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        } catch (XMLDOMException e) {
            System.err.println("XMLDOMException " + e.getMessage());
        } catch (XMLParseException e) {
            System.err.println("XMLParseException " + e.getMessage());
        } catch (XSLException e) {
            System.err.println("XSLException " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("SAXException " + e.getMessage());
        }
    }

    class CustomNSResolver implements NSResolver {
        public java.lang.String resolveNamespacePrefix(java.lang.String prefix) {
            if (prefix.equals("journal")) {
                return "http://www.xdk11g.com/xpath";
            } else
                return null;
        }
    }

    public static void main(String[] argv) {
        XPathParser parser = new XPathParser();
        parser.parseDocument(new File("catalog.xml"));
    }
}

