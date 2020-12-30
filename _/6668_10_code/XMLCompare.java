package xmldiff;

import oracle.xml.parser.v2.*;
import oracle.xml.differ.*;
import java.io.*;
import org.xml.sax.SAXException;


public class XMLCompare extends XMLDiff {
    protected static XMLCompare xmlDiff;


    public void xmlCompare(XMLDocument document1, XMLDocument document2) {
        try {
            xmlDiff.setDocuments(document1, document2);
            boolean diff = xmlDiff.diff();
            if (diff == false)
                System.out.println("The XML documents are the same");
            else
                System.out.println("The XML documents are different");
            BufferedWriter bufferedWriter =
                new BufferedWriter(new FileWriter(new File("diff.txt")));
            xmlDiff.printDiffTree(1, bufferedWriter);
            bufferedWriter.flush();
            xmlDiff.generateXSLFile("diff.xslt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        try {
            DOMParser parser = new DOMParser();
            InputStream catalog1 =
                new FileInputStream(new File("catalog1.xml"));
            parser.parse(catalog1);
            XMLDocument xmlDocument1 = parser.getDocument();
            InputStream catalog2 =
                new FileInputStream(new File("catalog2.xml"));
            parser.parse(catalog2);
            XMLDocument xmlDocument2 = parser.getDocument();
            xmlDiff = new XMLCompare();
            xmlDiff.xmlCompare(xmlDocument1, xmlDocument2);
        } catch (XMLParseException e) {
            System.err.println(e.getMessage());
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
